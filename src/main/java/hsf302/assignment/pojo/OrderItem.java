package hsf302.assignment.pojo;

import hsf302.assignment.Enum.Size;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(
        name = "order_item_decorations",
        joinColumns = @JoinColumn(name = "order_item_id"),
        inverseJoinColumns = @JoinColumn(name = "decoration_id")
    )
    private List<Decoration> decorations;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @OneToMany(mappedBy = "orderItem")
    private List<OrderItemDecoration> orderItemDecorations;

    @Enumerated(EnumType.STRING)
    @Column(name = "size", length = 5)
    private Size size;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "measurement_id")
    private Measurement measurement;

    public void setOrder(Order order) {
        this.order = order;
    }

    // Manual getters/setters to avoid Lombok issues
    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<Decoration> getDecorations() {
        return decorations;
    }

    public void setDecorations(List<Decoration> decorations) {
        this.decorations = decorations;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal basePrice = product.getPrice();
        BigDecimal decorationsPrice = BigDecimal.ZERO;

        if (decorations != null) {
            for (Decoration decoration : decorations) {
                decorationsPrice = decorationsPrice.add(decoration.getExtraPrice());
            }
        }

        return basePrice.add(decorationsPrice).multiply(BigDecimal.valueOf(quantity));
    }
}
