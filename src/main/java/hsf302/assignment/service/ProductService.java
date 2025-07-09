package hsf302.assignment.service;

import hsf302.assignment.pojo.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(Integer  id);
    Product save(Product product);
    void deleteById(Integer  id);
    // Có thể thêm các hàm tìm kiếm sau này
}