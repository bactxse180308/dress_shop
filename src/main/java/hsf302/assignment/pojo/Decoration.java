package hsf302.assignment.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "decorations")
public class Decoration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be positive or zero")
    @Column(name = "extra_price", precision = 10, scale = 2)
    private BigDecimal extraPrice;

    @OneToMany(mappedBy = "decoration", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItemDecoration> orderItemDecorations = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(BigDecimal extraPrice) {
        this.extraPrice = extraPrice;
    }

    public List<OrderItemDecoration> getOrderItemDecorations() {
        return orderItemDecorations;
    }

    public void setOrderItemDecorations(List<OrderItemDecoration> orderItemDecorations) {
        this.orderItemDecorations = orderItemDecorations;
    }
}
