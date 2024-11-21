package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @Column(name = "expiry_date" ,nullable = false)
    private LocalDate expiryDate;
    /////
    @Column(name = "discounted_price")
    private BigDecimal discountedPrice;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at",  nullable = false)
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductDamage> productDamages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    public BigDecimal getDiscountedPrice() {
        if (discount != null) {
            return discount.getDiscountedPrice(this);
        }
        return price;
    }

    @PrePersist
    private void oncreate(){

        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        updatedAt = createdAt;
        updateDiscountedPrice();
    }

    @PreUpdate
    private void onUpdate(){

        updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        updateDiscountedPrice();
    }


    private void updateDiscountedPrice() {
        this.discountedPrice = getDiscountedPrice();
    }

}
