package hsf302.assignment.pojo;

import hsf302.assignment.Enum.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "status", nullable = false)
    private OrderStatusEnum status; // pending, processing, shipped, completed, cancelled

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "shipping_address_detail", columnDefinition = "NVARCHAR(200)")
    private String shippingAddressDetail;

    @Column(name = "city", columnDefinition = "NVARCHAR(50)")
    private String city;

    @Column(name = "ward", columnDefinition = "NVARCHAR(50)")
    private String ward;

    @Column(name = "payment_method", columnDefinition = "NVARCHAR(50)")
    private String paymentMethod;

    @Column(name = "customer_name", columnDefinition = "NVARCHAR(255)")
    private String customerName;

    @Column(name = "customer_phone", columnDefinition = "NVARCHAR(50)")
    private String customerPhone;

    @Column(name = "customer_email", columnDefinition = "NVARCHAR(255)")
    private String customerEmail;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @PrePersist
    public void prePersist() {
        if (this.orderDate == null) {
            this.orderDate = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = OrderStatusEnum.Pending;
        }
    }

    // Getters and Setters

}
