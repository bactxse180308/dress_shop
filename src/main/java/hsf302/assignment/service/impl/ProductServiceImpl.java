package hsf302.assignment.service.impl;

import hsf302.assignment.pojo.Product;
import hsf302.assignment.repository.ProductRepository;
import hsf302.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> filterProducts(String name, Integer styleId, Integer fabricId, BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findAll().stream()
                .filter(product -> {
                    if (name != null && !name.isBlank()) {
                        if (!product.getName().toLowerCase().contains(name.toLowerCase())) return false;
                    }
                    if (styleId != null && (product.getStyle() == null || !product.getStyle().getId().equals(styleId))) {
                        return false;
                    }
                    if (fabricId != null && (product.getFabric() == null || !product.getFabric().getId().equals(fabricId))) {
                        return false;
                    }
                    if (minPrice != null && product.getPrice().compareTo(minPrice) < 0) {
                        return false;
                    }
                    if (maxPrice != null && product.getPrice().compareTo(maxPrice) > 0) {
                        return false;
                    }
                    return true;
                })
                .toList();
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findById1(Integer id) {
        return productRepository.findById(id)
                .map(List::of)
                .orElse(List.of());
    }

    @Override
    public Product save(Product product) {
        // Đảm bảo quan hệ hai chiều được thiết lập đúng cách
        if (product.getReadyMadeDress() != null) {
            product.getReadyMadeDress().setProduct(product);
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByStyle(String styleName) {
        return productRepository.findByStyle_Name(styleName);
    }

    @Override
    public List<Product> findByFabric(String fabricName) {
        return productRepository.findByFabric_Name(fabricName);
    }

    @Override
    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Product> filterByPriceRange(BigDecimal min, BigDecimal max) {
        return productRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Product> getNewestProducts() {
        return productRepository.findTop10ByOrderByCreatedAtDesc();
    }

    @Override
    public Map<String, List<Product>> getProductsGroupedByFabric() {
        List<Product> allProducts = productRepository.findAll();

        return allProducts.stream()
                .filter(product -> product.getFabric() != null && product.getFabric().getName() != null)
                .collect(Collectors.groupingBy(product -> product.getFabric().getName()));
    }
}