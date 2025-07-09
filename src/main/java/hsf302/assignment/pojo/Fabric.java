package hsf302.assignment.pojo;

import hsf302.assignment.pojo.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "fabrics")
@Getter
@Setter
public class Fabric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", columnDefinition = "NVARCHAR(100)")
    private String name;

    @Column(columnDefinition = "NVARCHAR(MAX)", name = "description")
    private String description;

    @OneToMany(mappedBy = "fabric")
    private List<Product> products;

    // Getters and Setters
}
