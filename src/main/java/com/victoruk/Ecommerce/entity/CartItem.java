package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private int quantity = 1;

        @Column(name = "price" , nullable = false, precision = 10, scale = 2)
        private BigDecimal price;

        @Column(name = "created_at", nullable = false)
        private LocalDateTime createdAt;

        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @PrePersist
    private void onCreate(){

        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }


    @PreUpdate
    private void onUpdate(){

        updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

     // price for this CartItem

    public void calculatePrice() {
        this.price = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }


}
