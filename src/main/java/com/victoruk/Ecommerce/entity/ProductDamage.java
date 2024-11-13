package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@Table(name = "product_damages")
public class ProductDamage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity_damaged", nullable = false)
    private String quantityDamaged;

    @CreatedDate
    @Column(name = "damage_date" , nullable = false)
    private LocalDate damageDate;

    @Column(name = "reason")
    private String reason;

    @Column(name = "photo_url")
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


//    @PrePersist
//    private void oncreate(){
//
//        damageDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
//    }


}
