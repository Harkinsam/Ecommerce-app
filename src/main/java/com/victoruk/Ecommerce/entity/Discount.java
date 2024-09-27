package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount_percentage")
    private BigDecimal discountPercentage;

    @Column(name = "start_date" , nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date" , nullable = false)
    private LocalDate endDate;

//    @Column(name = "created_at" , nullable = false)
//    private LocalDate createdAt;
//
//    @Column(name = "updated_at" , nullable = false)
//    private LocalDate updatedAt;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
