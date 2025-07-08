package hsf302.assignment.repository;

import hsf302.assignment.pojo.Order;
import hsf302.assignment.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);
}
