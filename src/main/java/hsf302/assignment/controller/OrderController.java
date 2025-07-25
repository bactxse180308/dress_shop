package hsf302.assignment.controller;

import hsf302.assignment.Enum.OrderStatusEnum;
import hsf302.assignment.pojo.*;
import hsf302.assignment.service.*;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class OrderController {

    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final UserService userService;
    private final OrderService orderService;
    private final MeasurementService measurementService;
    private final ReviewService reviewService;

    @Autowired
    public OrderController(CartService cartService, OrderItemService orderItemService,
                           UserService userService, OrderService orderService, MeasurementService measurementService, ReviewService reviewService) {
        this.cartService = cartService;
        this.orderItemService = orderItemService;
        this.userService = userService;
        this.orderService = orderService;
        this.measurementService = measurementService;
        this.reviewService = reviewService;
    }

    // Đặt hàng
    @PostMapping("/order/checkout")
    public String checkout(
            @RequestParam Long userId,
            @RequestParam String customerName,
            @RequestParam String customerPhone,
            @RequestParam String customerEmail,
            @RequestParam String shippingAddressDetail,
            @RequestParam String city,
            @RequestParam String ward,
            @RequestParam String paymentMethod,
            @RequestParam(required = false) String transactionCode,  // Mã giao dịch cho phương thức chuyển khoản
            RedirectAttributes redirectAttributes
    ) {
        // Tạo đơn hàng
        User user = userService.getUserById(userId.intValue());
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatusEnum.Pending);
        order.setTotalAmount(cartService.calculateTotal());

        // Lưu thông tin khách hàng và giao hàng
        order.setCustomerName(customerName);
        order.setCustomerPhone(customerPhone);
        order.setCustomerEmail(customerEmail);
        order.setShippingAddressDetail(shippingAddressDetail);
        order.setCity(city);
        order.setWard(ward);
        order.setPaymentMethod(paymentMethod);

        // Xử lý phương thức thanh toán
        if ("bank".equals(paymentMethod)) {
            order.setPaymentMethod("Chờ xác nhận");
            order.setPaymentMethod(transactionCode);  // Lưu mã giao dịch cho chuyển khoản ngân hàng
        } else {
            order.setPaymentMethod("Đã thanh toán");
        }

        // Lưu đơn hàng vào DB
        orderService.saveOrder(order);

        // Lưu các sản phẩm trong giỏ hàng
        for (OrderItem item : cartService.getCartItems()) {
            item.setOrder(order);
            if (item.getUnitPrice() == null) {
                item.setUnitPrice(item.getProduct().getPrice()); // Đảm bảo không để giá trị NULL
            }
            Measurement m = item.getMeasurement();
            if (item.getMeasurement() != null && item.getMeasurement().getId() == null) {
                measurementService.createMeasurement(item.getMeasurement());  // Đảm bảo bạn có MeasurementRepository
            }
            orderItemService.saveOrderItem(item);  // Lưu từng sản phẩm
        }

        // Xóa giỏ hàng
        cartService.clearCart();

        // Thêm thông báo thành công
        redirectAttributes.addFlashAttribute("message", "Đặt hàng thành công!");

        // Chuyển hướng đến trang danh sách đơn hàng với userId
        return "redirect:/order/list?userId=" + userId;
    }

    // Xem danh sách đơn hàng
    @GetMapping("/order/list")
    public String listOrders(@RequestParam Long userId, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "access-denied"; // Redirect to access denied page if session is not valid
        }
        User user = userService.getUserById(userId.intValue());

        List<Order> orders = orderService.findByUser(user);
        model.addAttribute("orders", orders);
        model.addAttribute("userId", userId);
        return "order_list";
    }

    // Xem chi tiết đơn hàng
    @GetMapping("/order/detail/{id}")
    public String orderDetail(@PathVariable Integer id, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "access-denied"; // Redirect to access denied page if session is not valid
        }
        System.out.println("=== DEBUG OrderController.orderDetail ===");
        System.out.println("Order ID: " + id);
        
        List<Order> orders = orderService.findById(id);
        if (!orders.isEmpty()) {
            Order order = orders.get(0);
            System.out.println("Found order: " + order.getId());
            
            // Lấy userId từ order (giả sử order.getUser().getId())
            Integer userId = order.getUser().getId();
            System.out.println("User ID: " + userId);
            
            // Lấy danh sách các sản phẩm trong đơn hàng
            List<OrderItem> items = order.getOrderItems();
            System.out.println("Number of order items: " + items.size());
            
            // Lấy đánh giá theo đơn hàng cụ thể thay vì theo user
            Map<Integer, Review> reviewsMap = reviewService.getReviewsMapByOrder(id);
            System.out.println("Reviews map for order " + id + ": " + reviewsMap);
            System.out.println("Reviews map size: " + reviewsMap.size());
            
            // Chuyển đổi sang DTO để tránh circular reference
            Map<Integer, hsf302.assignment.dto.ReviewDTO> reviewedProducts = new HashMap<>();
            for (Map.Entry<Integer, Review> entry : reviewsMap.entrySet()) {
                Review review = entry.getValue();
                if (review != null) {
                    hsf302.assignment.dto.ReviewDTO dto = new hsf302.assignment.dto.ReviewDTO(
                        review.getId(),
                        review.getRating(),
                        review.getComment(),
                        review.getCreatedAt(),
                        review.getProduct().getId(),
                        review.getUser().getId()
                    );
                    reviewedProducts.put(entry.getKey(), dto);
                    System.out.println("Added ReviewDTO for product " + entry.getKey() + ": " + dto);
                }
            }
            
            model.addAttribute("order", order);
            model.addAttribute("reviewedProducts", reviewedProducts);
        } else {
            System.out.println("Order not found with ID: " + id);
            model.addAttribute("errorMessage", "Không tìm thấy đơn hàng.");
        }
        return "order_detail";
    }

    // Admin - Xem danh sách đơn hàng
    @GetMapping("/admin/orders")
    public String listOrders(Model model, HttpSession session) {
        if (session.getAttribute("user") == null || !session.getAttribute("userRole").equals("ADMIN")) {
            return "access-denied"; // Redirect to access denied page if session is not valid
        }
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/order-manager";
    }

    // Admin - Xem chi tiết đơn hàng
    @GetMapping("/admin/orders/{id}")
    public String viewOrderDetail(@PathVariable Integer id, Model model , HttpSession session) {
        if (session.getAttribute("user") == null || !session.getAttribute("userRole").equals("ADMIN")) {
            return "access-denied"; // Redirect to access denied page if session is not valid
        }
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "admin/order-detail";
        }
        return "redirect:/admin/orders";
    }

    // Admin - Cập nhật trạng thái đơn hàng
    @PutMapping("/admin/orders/{id}/status")
    public String updateOrderStatus(
            @PathVariable Integer id,
            @RequestParam OrderStatusEnum status,
            @RequestParam(required = false) String returnUrl,
            HttpSession session
    ) {
        if (session.getAttribute("user") == null || !session.getAttribute("userRole").equals("ADMIN")) {
            return "access-denied"; // Redirect to access denied page if session is not valid
        }
        try {
            orderService.updateOrderStatus(id, status);
        } catch (Exception e) {
            // Log the error if needed
            e.printStackTrace();
        }
        return returnUrl != null ? "redirect:" + returnUrl : "redirect:/admin/orders";
    }

    // Admin - Xóa đơn hàng
    @GetMapping("/admin/orders/{id}/delete")
    public String deleteOrder(@PathVariable Integer id, @RequestParam(required = false) String returnUrl, HttpSession session) {
        if (session.getAttribute("user") == null || !session.getAttribute("userRole").equals("ADMIN")) {
            return "access-denied"; // Redirect to access denied page if session is not valid
        }
        try {
            orderService.deleteOrder(id);
        } catch (Exception e) {
            // Log the error if needed
            e.printStackTrace();
        }
        return returnUrl != null ? "redirect:" + returnUrl : "redirect:/admin/orders";
    }

    // User - Xóa đơn hàng (chỉ cho phép xóa đơn Complete hoặc Cancelled)
    @PostMapping("/order/delete/{id}")
    @Transactional
    public String deleteUserOrder(@PathVariable Integer id, @RequestParam Long userId, RedirectAttributes redirectAttributes, HttpSession session) {
        if (session.getAttribute("user") == null || !session.getAttribute("userRole").equals("ADMIN")) {
            return "access-denied"; // Redirect to access denied page if session is not valid
        }
        try {
            System.out.println("=== DEBUG: Bắt đầu xóa đơn hàng ===");
            System.out.println("Order ID: " + id);
            System.out.println("User ID: " + userId);
            
            // Kiểm tra đơn hàng có tồn tại và thuộc về user này không
            Optional<Order> optionalOrder = orderService.getOrderById(id);
            if (optionalOrder.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy đơn hàng #" + id);
                return "redirect:/order/list?userId=" + userId;
            }
            
            Order order = optionalOrder.get();
            
            // Kiểm tra quyền sở hữu
            if (!order.getUser().getId().equals(userId.intValue())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Bạn không có quyền xóa đơn hàng này");
                return "redirect:/order/list?userId=" + userId;
            }
            
            // Kiểm tra trạng thái đơn hàng
            OrderStatusEnum status = order.getStatus();
            if (status != OrderStatusEnum.Completed && status != OrderStatusEnum.Cancelled) {
                redirectAttributes.addFlashAttribute("errorMessage", "Chỉ có thể xóa đơn hàng đã hoàn thành hoặc đã hủy");
                return "redirect:/order/list?userId=" + userId;
            }
            
            System.out.println("Order Status: " + status);
            System.out.println("Cho phép xóa đơn hàng");
            
            // Thực hiện xóa đơn hàng
            orderService.deleteOrder(id);
            System.out.println("Đã gọi deleteOrder method");
            
            redirectAttributes.addFlashAttribute("message", "Đã xóa thành công đơn hàng #" + id);
            
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa đơn hàng: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi xóa đơn hàng: " + e.getMessage());
        }
        return "redirect:/order/list?userId=" + userId;
    }


}
