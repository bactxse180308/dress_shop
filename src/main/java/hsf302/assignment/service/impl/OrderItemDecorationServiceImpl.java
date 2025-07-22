package hsf302.assignment.service.impl;

import hsf302.assignment.pojo.OrderItemDecoration;
import hsf302.assignment.repository.OrderItemDecorationRepository;
import hsf302.assignment.service.OrderItemDecorationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemDecorationServiceImpl implements OrderItemDecorationService {

    private final OrderItemDecorationRepository repository;

    @Override
    public List<OrderItemDecoration> getAll() {
        return repository.findAll();
    }

    @Override
    public OrderItemDecoration getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItemDecoration not found with id: " + id));
    }

    @Override
    public OrderItemDecoration create(OrderItemDecoration entity) {
        return repository.save(entity);
    }

    @Override
    public OrderItemDecoration update(Integer id, OrderItemDecoration newEntity) {
        OrderItemDecoration existing = getById(id);
        existing.setOrderItem(newEntity.getOrderItem());
        existing.setDecoration(newEntity.getDecoration());
        return repository.save(existing);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
