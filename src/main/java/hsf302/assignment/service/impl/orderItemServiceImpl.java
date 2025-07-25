package hsf302.assignment.service.impl;

import hsf302.assignment.pojo.Decoration;
import hsf302.assignment.pojo.OrderItem;
import hsf302.assignment.repository.OrderItemRepository;
import hsf302.assignment.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class orderItemServiceImpl implements OrderItemService {
    private final List<OrderItem> cartItems = new ArrayList<>();

    private OrderItemRepository orderItemRepository;
    @Override
    public List<OrderItem> getAllOrderItems() {
        // Implementation logic here
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(Integer id) {
        // Implementation logic here
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + id));
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        // Implementation logic here
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Integer id) {
        // Implementation logic here
        if (!orderItemRepository.existsById(id)) {
            throw new RuntimeException("OrderItem not found with id: " + id);
        }
        orderItemRepository.deleteById(id);
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        // Implementation logic here
        if (!orderItemRepository.existsById(orderItem.getId())) {
            throw new RuntimeException("OrderItem not found with id: " + orderItem.getId());
        }
        orderItemRepository.save(orderItem);
    }

    @Override
    public void saveOrderItem(OrderItem item) {
        // Implementation logic here
        orderItemRepository.save(item);

    }
    @Override
    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;

        // Lặp qua từng item trong giỏ hàng
        for (OrderItem item : cartItems) {// Lấy giá cơ bản của sản phẩm
            BigDecimal basePrice = item.getProduct().getPrice();// Tính tổng giá phụ kiện (nếu có)
            BigDecimal decorationsPrice = BigDecimal.ZERO;
            if (item.getDecorations() != null) {
                for (Decoration decoration : item.getDecorations()) {
                    decorationsPrice = decorationsPrice.add(decoration.getExtraPrice());
                }
            }// Tính giá trị của item (giá cơ bản + giá phụ kiện) nhân với số lượng
            BigDecimal itemTotal = basePrice.add(decorationsPrice).multiply(BigDecimal.valueOf(item.getQuantity()));
            // Cộng vào tổng
            total = total.add(itemTotal);
        }

        return total;
    }


}
