package hsf302.assignment.repository;

import hsf302.assignment.pojo.Order;
import hsf302.assignment.pojo.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder(Order order);
    void deleteByOrder(Order order);
}
