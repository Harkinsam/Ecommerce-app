package com.victoruk.Ecommerce.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "created_at")
    private LocalDateTime createdDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CartItem> cartItems = new ArrayList<>();

    @PrePersist
    private void onCreate(){

        createdDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    @PreUpdate
    private void onUpdate(){

        updatedDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public void calculateTotalPrice() {
        totalPrice = cartItems.stream()
                .map(cartItem -> cartItem.getPrice()) // Lambda for method reference CartItem::getPrice
                .filter(price -> price != null)       // Lambda for method reference Objects::nonNull
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b)); // Lambda for method reference BigDecimal::add
    }

}
