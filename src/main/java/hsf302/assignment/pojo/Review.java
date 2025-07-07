package hsf302.assignment.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer rating;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
