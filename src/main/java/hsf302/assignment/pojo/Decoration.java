package hsf302.assignment.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "decorations")
@Getter
@Setter
public class Decoration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(columnDefinition = "NVARCHAR(MAX)", name = "description")
    private String description;

    @Column(name = "extra_price")
    private BigDecimal extraPrice;

    @OneToMany(mappedBy = "decoration")
    private List<OrderItemDecoration> orderItemDecorations;

    // Getters and Setters
}
