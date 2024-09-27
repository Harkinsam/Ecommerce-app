package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "product_damages")
public class ProductDamage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity_damaged", nullable = false)
    private int quantityDamaged;

    @Column(name = "damage_date")
    private LocalDate damageDate;

    @Column(name = "reason")
    private String reason;

    @Column(name = "photo_url")
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
