package hsf302.assignment.service;

import hsf302.assignment.pojo.Measurement;
import hsf302.assignment.pojo.OrderItem;
import hsf302.assignment.pojo.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    public void addToCart(Product product, int quantity, Measurement measurement);
    public List<OrderItem> getCartItems();
    public void clearCart();
    public BigDecimal calculateTotal();
}
