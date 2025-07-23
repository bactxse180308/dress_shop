package hsf302.assignment.controller;

import hsf302.assignment.Enum.OrderStatusEnum;
import hsf302.assignment.pojo.Order;
import hsf302.assignment.pojo.OrderItem;
import hsf302.assignment.pojo.User;
import hsf302.assignment.repository.OrderItemRepository;
import hsf302.assignment.repository.OrderRepository;
import hsf302.assignment.repository.UserRepository;
import hsf302.assignment.service.CartService;
import hsf302.assignment.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class OrderController {


    private CartService cartService;

    private OrderRepository orderRepository;

    private OrderItemRepository orderItemRepository;

    private UserRepository userRepository;
    private OrderService orderService;

    // Đặt hàng
    @PostMapping("/order/checkout")
    public String checkout(@RequestParam Long userId) {
        // Lấy user từ DB
        User user = userRepository.findById(userId.intValue())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tạo đơn hàng
        hsf302.assignment.pojo.Order order = new hsf302.assignment.pojo.Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatusEnum.Pending);

        order.setTotalAmount(cartService.calculateTotal());

        orderRepository.save(order);

        // Lưu các sản phẩm trong giỏ
        for (OrderItem item : cartService.getCartItems()) {
            item.setOrder(order);
            orderItemRepository.save(item);
        }

        cartService.clearCart();

        return "redirect:/order/list?userId=" + userId;
    }

    // Xem danh sách đơn hàng
    @GetMapping("/order/list")
    public String listOrders(@RequestParam Long userId, Model model) {
        User user = userRepository.findById(userId.intValue())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);
        model.addAttribute("orders", orders);
        model.addAttribute("userId", userId); // để dùng khi link
        return "order_list";
    }

    // Xem chi tiết đơn hàng
    @GetMapping("/order/detail/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id.intValue())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        model.addAttribute("order", order);
        return "order_detail";
    }

    @GetMapping("/admin/orders")
    public String listOrders(
            Model model
    ) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/order-manager";
    }

    @GetMapping("/admin/orders/{id}")
    public String viewOrderDetail(@PathVariable Integer id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "admin/order-detail";
        }
        return "redirect:/admin/orders";
    }

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
        }
        return returnUrl != null ? "redirect:" + returnUrl : "redirect:/admin/orders";
    }

    @GetMapping("/admin/orders/{id}/delete")
    public String deleteOrder(@PathVariable Integer id, @RequestParam(required = false) String returnUrl) {
        try {
            orderService.deleteOrder(id);
        } catch (Exception e) {
            // Log the error if needed
        }
        return returnUrl != null ? "redirect:" + returnUrl : "redirect:/admin/orders";
    }

}
