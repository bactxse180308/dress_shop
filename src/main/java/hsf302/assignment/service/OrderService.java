package hsf302.assignment.service;

import hsf302.assignment.Enum.OrderStatusEnum;
import hsf302.assignment.pojo.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders();
    
    Optional<Order> getOrderById(Integer id);

    
    Order updateOrderStatus(Integer orderId, OrderStatusEnum newStatus);
    
    void deleteOrder(Integer id);
    
    Order saveOrder(Order order);


}
