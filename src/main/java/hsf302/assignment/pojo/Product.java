package hsf302.assignment.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    private Double price;

    private String imageUrl;

    @Column(length = 20)
    private String productType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "fabric_id")
    private Fabric fabric;

    @ManyToOne
    @JoinColumn(name = "style_id")
    private Style style;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ReadyMadeDress readyMadeDress;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private CustomDress customDress;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;
}
