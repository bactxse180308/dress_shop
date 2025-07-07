package hsf302.assignment.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    private String passwordHash;

    private String phone;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Measurement> measurements;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
}
