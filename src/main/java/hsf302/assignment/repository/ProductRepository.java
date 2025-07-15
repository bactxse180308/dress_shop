package hsf302.assignment.repository;

import hsf302.assignment.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByStyle_Name(String styleName);
    List<Product> findByFabric_Name(String fabricName);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
    List<Product> findTop10ByOrderByCreatedAtDesc();
}
