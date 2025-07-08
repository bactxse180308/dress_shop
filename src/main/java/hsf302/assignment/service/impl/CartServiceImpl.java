package hsf302.assignment.service.impl;

import hsf302.assignment.pojo.Measurement;
import hsf302.assignment.pojo.OrderItem;
import hsf302.assignment.pojo.Product;
import hsf302.assignment.service.CartService;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartServiceImpl implements CartService {
    private List<OrderItem> cartItems = new ArrayList<>();

    public void addToCart(Product product, int quantity, Measurement measurement) {
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setMeasurement(measurement);
        cartItems.add(item);
    }

    public List<OrderItem> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public BigDecimal calculateTotal() {
        return cartItems.stream()
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
