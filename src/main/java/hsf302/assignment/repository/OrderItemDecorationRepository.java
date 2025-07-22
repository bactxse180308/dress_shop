package hsf302.assignment.repository;

import hsf302.assignment.pojo.OrderItemDecoration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDecorationRepository extends JpaRepository<OrderItemDecoration, Integer> {
}
