package hsf302.assignment.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "custom_dresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomDress {
    @Id
    private Integer productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer tailoringTimeDays;
}
