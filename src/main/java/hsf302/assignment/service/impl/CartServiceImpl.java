package hsf302.assignment.service.impl;

import hsf302.assignment.pojo.Measurement;
import hsf302.assignment.pojo.OrderItem;
import hsf302.assignment.pojo.Product;
import hsf302.assignment.service.CartService;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Service
@SessionScope
public class CartServiceImpl implements CartService {
    private final List<OrderItem> cartItems = new ArrayList<>();

    public void addToCart(Product product, int quantity, Measurement measurement) {
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setMeasurement(measurement);
        for (OrderItem orderItem : cartItems) {
            if (orderItem.getProduct().getId().equals(product.getId()) && orderItem.getMeasurement().getNote().equals(measurement.getNote())) {
                orderItem.setQuantity(orderItem.getQuantity() + quantity);
                return; // Exit if the product is already in the cart
            }
        }
        cartItems.add(item);
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
