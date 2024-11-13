package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private String method;
    private String status;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private  Order order;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    @PrePersist
    private void oncreate(){

        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    @PreUpdate
    private void onUpdate(){

        updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }
}
