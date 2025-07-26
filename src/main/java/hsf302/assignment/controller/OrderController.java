package hsf302.assignment.controller;

import hsf302.assignment.Enum.OrderStatusEnum;
import hsf302.assignment.pojo.Measurement;
import hsf302.assignment.pojo.Order;
import hsf302.assignment.pojo.OrderItem;
import hsf302.assignment.pojo.User;
import hsf302.assignment.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final UserService userService;
    private final OrderService orderService;
    private final MeasurementService measurementService;

    @Autowired
    public OrderController(CartService cartService, OrderItemService orderItemService,
                           UserService userService, OrderService orderService, MeasurementService measurementService) {
        this.cartService = cartService;
        this.orderItemService = orderItemService;
        this.userService = userService;
        this.orderService = orderService;
        this.measurementService = measurementService;
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
            order.setPaymentMethod(transactionCode);
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
    public String listOrders(@RequestParam Long userId, Model model) {
        User user = userService.getUserById(userId.intValue());

        List<Order> orders = orderService.findByUser(user);
        model.addAttribute("orders", orders);
        model.addAttribute("userId", userId);
        return "order_list";
    }

    // Xem chi tiết đơn hàng
    @GetMapping("/order/detail/{id}")
    public String orderDetail(@PathVariable Integer id, Model model) {
        List<Order> order = orderService.findById(id);
        if (!order.isEmpty()) {
            model.addAttribute("order", order.get(0));
        } else {
            model.addAttribute("errorMessage", "Không tìm thấy đơn hàng.");
        }
        return "order_detail";
    }

    // Admin - Xem danh sách đơn hàng
    @GetMapping("/admin/orders")
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/order-manager";
    }

    // Admin - Xem chi tiết đơn hàng
    @GetMapping("/admin/orders/{id}")
    public String viewOrderDetail(@PathVariable Integer id, Model model) {
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
            @RequestParam(required = false) String returnUrl
    ) {
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
    public String deleteOrder(@PathVariable Integer id, @RequestParam(required = false) String returnUrl) {
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
    public String deleteUserOrder(@PathVariable Integer id, @RequestParam Long userId, RedirectAttributes redirectAttributes) {
        try {
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
            // Thực hiện xóa đơn hàng
            orderService.deleteOrder(id);
            redirectAttributes.addFlashAttribute("message", "Đã xóa thành công đơn hàng #" + id);
            
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa đơn hàng: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi xóa đơn hàng: " + e.getMessage());
        }
        return "redirect:/order/list?userId=" + userId;
    }
}
