package hsf302.assignment.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "fabrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fabric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @OneToMany(mappedBy = "fabric")
    private List<Product> products;
}
