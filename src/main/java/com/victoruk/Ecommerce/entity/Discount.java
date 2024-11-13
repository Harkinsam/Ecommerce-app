package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal discountPercentage;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @PrePersist
    private void onCreate() {
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
        if (endDate == null) {
            endDate = startDate.plusDays(30);
        }
    }


    public BigDecimal getDiscountedPrice(Product product) {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Check if the discount is active based on the start and end dates
        if (now.isAfter(startDate) && now.isBefore(endDate)) {
            // Calculate the discounted multiplier as (1 - discountPercentage / 100)
            BigDecimal discountMultiplier = BigDecimal.ONE.subtract(
                    discountPercentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
            );

            // Apply the discount to the product's price
            return product.getPrice().multiply(discountMultiplier).setScale(2, RoundingMode.HALF_UP);
        }

        // Return original price if discount is not active
        return product.getPrice();
    }


//    public BigDecimal getDiscountedPrice(Product product) {
//        // Check if discount is active
//        LocalDateTime now = LocalDateTime.now();
//        if (now.isAfter(startDate) && now.isBefore(endDate)) {
//            BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discountPercentage.divide(BigDecimal.valueOf(100)));
//            return product.getPrice().multiply(discountMultiplier);
//        }
//        return product.getPrice();
//    }

}
