package hsf302.assignment.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "measurements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double bustSize;
    private Double waistSize;
    private Double hipsSize;
    private Double buttSize;
    private Double skirtLength;
    private Double shoulderWidth;
    private Double armLength;
    private Double waistDrop;
    private Double hipDrop;
    private Double neckSize;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String note;

    @OneToMany(mappedBy = "measurement")
    private java.util.List<OrderItem> orderItems;
}
