package hsf302.assignment.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ready_made_dresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadyMadeDress {
    @Id
    private Integer productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    private String size;

    private Integer quantityInStock;
}
