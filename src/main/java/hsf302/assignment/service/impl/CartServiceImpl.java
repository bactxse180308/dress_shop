package hsf302.assignment.service.impl;

import hsf302.assignment.Enum.Size;
import hsf302.assignment.pojo.Decoration;
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

    public void addToCart(Product product, int quantity,
                          Size size,           // <-- mới
                          Measurement measurement,
                          List<Decoration> decorations) {
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setSize(size);               // <-- gán size
        item.setMeasurement(measurement);
        item.setDecorations(decorations);

        // 2. Khi merge, so sánh productId + size + measurement + decorations
        for (OrderItem existing : cartItems) {
            boolean sameProduct = existing.getProduct().getId().equals(product.getId());
            boolean sameSize    = existing.getSize() == size;
            boolean sameMeasure = (existing.getMeasurement() == null && measurement == null)
                    || (existing.getMeasurement() != null && measurement != null
                    && existing.getMeasurement().getNote().equals(measurement.getNote()));
            boolean sameDeco    = existing.getDecorations().equals(decorations);

            if (sameProduct && sameSize && sameMeasure && sameDeco) {
                existing.setQuantity(existing.getQuantity() + quantity);
                return;
            }
        }

        cartItems.add(item);
    }

    public void clearCart() {
        cartItems.clear();
    }

    @Override
    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;

        // Lặp qua từng item trong giỏ hàng
        for (OrderItem item : cartItems) {// Lấy giá cơ bản của sản phẩm
            BigDecimal basePrice = item.getProduct().getPrice();// Tính tổng giá phụ kiện (nếu có)
            BigDecimal decorationsPrice = BigDecimal.ZERO;
            if (item.getDecorations() != null) {
                for (Decoration decoration : item.getDecorations()) {
                    decorationsPrice = decorationsPrice.add(decoration.getExtraPrice());
                }
            }// Tính giá trị của item (giá cơ bản + giá phụ kiện) nhân với số lượng
            BigDecimal itemTotal = basePrice.add(decorationsPrice).multiply(BigDecimal.valueOf(item.getQuantity()));

            // Cộng vào tổng
            total = total.add(itemTotal);
        }

        return total;
    }

    @Override
    public void updateItemMeasurement(int itemIndex, Measurement measurement) {
        if (itemIndex >= 0 && itemIndex < cartItems.size()) {
            OrderItem item = cartItems.get(itemIndex);
            item.setMeasurement(measurement);
        }
    }

}
