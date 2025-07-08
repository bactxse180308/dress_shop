package hsf302.assignment.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "measurements")
@Getter
@Setter
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

    @Column(length = 500, name = "note")
    private String note;


    // Getters and Setters
}
