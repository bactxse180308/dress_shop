package hsf302.assignment.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, columnDefinition = "NVARCHAR(100)")
    private String name;

    @Column(columnDefinition = "NVARCHAR(MAX)", name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>(); // Khởi tạo sẵn để tránh NullPointerException

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "fabric_id")
    private Fabric fabric;

    @ManyToOne
    @JoinColumn(name = "style_id")
    private Style style;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ReadyMadeDress readyMadeDress;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    // Getters and Setters
}
