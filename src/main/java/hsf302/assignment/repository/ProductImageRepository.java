package hsf302.assignment.repository;

import hsf302.assignment.pojo.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
}