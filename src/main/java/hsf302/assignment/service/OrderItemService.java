package hsf302.assignment.service;

import hsf302.assignment.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {
    // Define methods for order item service operations
    // For example:
     List<OrderItem> getAllOrderItems();
     OrderItem getOrderItemById(Integer id);
     OrderItem createOrderItem(OrderItem orderItem);
     void deleteOrderItem(Integer id);
     void updateOrderItem(OrderItem orderItem);

 void saveOrderItem(OrderItem item);
}
