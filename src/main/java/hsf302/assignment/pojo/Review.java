package hsf302.assignment.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import hsf302.assignment.Enum.ReviewStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "rating", nullable = false)
    @JsonProperty("rating")
    private Integer rating;

    @Column(length = 500, name = "comment")
    @JsonProperty("comment")
    private String comment;

    @Column(name = "created_at", updatable = false)
    @JsonProperty("createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    private ReviewStatusEnum reviewStatus;

    public Review(Product product, User user, Integer rating, String content, ReviewStatusEnum reviewStatus) {
        this.product = product;
        this.user = user;
        this.rating = rating;
        this.comment = content;
        this.reviewStatus = reviewStatus;
    }
}
