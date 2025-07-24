package hsf302.assignment.service.impl;

import hsf302.assignment.pojo.OrderItem;
import hsf302.assignment.repository.OrderItemRepository;
import hsf302.assignment.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class orderItemServiceImpl implements OrderItemService {

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
}
