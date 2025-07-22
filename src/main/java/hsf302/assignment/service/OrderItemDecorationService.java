package hsf302.assignment.service;

import hsf302.assignment.pojo.OrderItemDecoration;

import java.util.List;

public interface OrderItemDecorationService {
    List<OrderItemDecoration> getAll();
    OrderItemDecoration getById(Integer id);
    OrderItemDecoration create(OrderItemDecoration entity);
    OrderItemDecoration update(Integer id, OrderItemDecoration entity);
    void delete(Integer id);
}
