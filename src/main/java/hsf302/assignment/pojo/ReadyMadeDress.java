package hsf302.assignment.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ready_made_dresses")
@Getter
@Setter
public class ReadyMadeDress {
    @Id
    private Integer productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "size")
    private String size; // S, M, L, XL

    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;

    // Getters and Setters
}
