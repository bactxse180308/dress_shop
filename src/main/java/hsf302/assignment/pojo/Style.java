package hsf302.assignment.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "styles")
@Getter
@Setter
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Lob
    private String description;

    @OneToMany(mappedBy = "style")
    private List<Product> products;

    // Getters and Setters
}
