package hsf302.assignment.service;

import hsf302.assignment.Enum.Size;
import hsf302.assignment.pojo.Decoration;
import hsf302.assignment.pojo.Measurement;
import hsf302.assignment.pojo.OrderItem;
import hsf302.assignment.pojo.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    void addToCart(Product product,
                   int quantity,
                   Size size,
                   Measurement measurement,
                   List<Decoration> decorations);
    public List<OrderItem> getCartItems();
    public void clearCart();
    public BigDecimal calculateTotal();
    void updateItemMeasurement(int itemIndex, Measurement measurement);
}
