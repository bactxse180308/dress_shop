package hsf302.assignment.service;

import hsf302.assignment.pojo.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> filterProducts(String name, Integer styleId, Integer fabricId,
                                 BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> findAll();
    Optional<Product> findById(Integer  id);
    Product save(Product product);
    void deleteById(Integer  id);
    List<Product> findByStyle(String styleName);
    List<Product> findByFabric(String fabricName);
    List<Product> searchByName(String keyword);
    List<Product> filterByPriceRange(BigDecimal min, BigDecimal max);
    List<Product> getNewestProducts();

}