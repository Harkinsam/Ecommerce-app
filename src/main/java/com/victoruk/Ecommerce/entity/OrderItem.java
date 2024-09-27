package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "quantity", nullable = false)
        private int quantity;

        @Column(name = "price", precision = 10, scale = 2, nullable = false)
        private BigDecimal price;

        @Column(name = "discount_applied", precision = 10, scale = 2, nullable = false)
        private BigDecimal discountApplied = BigDecimal.ZERO;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", nullable = false, length = 20)
        private OrderItemStatus orderStatus ;

        @Column(name = "created_at", nullable = false)
        private LocalDate createdAt;

        @Column(name = "updated_at", nullable = false)
        private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
