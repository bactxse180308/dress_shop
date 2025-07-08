package hsf302.assignment.controller;

import hsf302.assignment.Enum.OrderStatusEnum;
import hsf302.assignment.pojo.Order;
import hsf302.assignment.pojo.OrderItem;
import hsf302.assignment.pojo.User;
import hsf302.assignment.repository.OrderItemRepository;
import hsf302.assignment.repository.OrderRepository;
import hsf302.assignment.repository.UserRepository;
import hsf302.assignment.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    // Đặt hàng
    @PostMapping("/checkout")
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
    @GetMapping("/list")
    public String listOrders(@RequestParam Long userId, Model model) {
        User user = userRepository.findById(userId.intValue())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);
        model.addAttribute("orders", orders);
        model.addAttribute("userId", userId); // để dùng khi link
        return "order_list";
    }

    // Xem chi tiết đơn hàng
    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id.intValue())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        model.addAttribute("order", order);
        return "order_detail";
    }
}
