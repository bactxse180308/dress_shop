package hsf302.assignment.service.impl;

import hsf302.assignment.pojo.Product;
import hsf302.assignment.repository.ProductRepository;
import hsf302.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
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
}