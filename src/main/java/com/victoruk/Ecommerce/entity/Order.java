package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
        private BigDecimal totalAmount;

        @Column(name = "order_confirmation_code", nullable = false, length = 20)
        private String orderConfirmationCode;

        @Enumerated(EnumType.STRING)
        @Column(name = "order_status", nullable = false, length = 20)
        private OrderStatus orderStatus;

        @Enumerated(EnumType.STRING)
        @Column(name = "payment_status", nullable = false, length = 20)
        private PaymentStatus paymentStatus;

        @Column(name = "created_at", nullable = false)
        private LocalDate createdAt;

        @Column(name = "updated_at", nullable = false)
        private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "order",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Address address;

}
