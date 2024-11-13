package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url",length = 500, nullable = false)
    private String imageUrl;

    @Column(name = "alt_text", nullable = false)
    private String altText;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;


    @PrePersist
    private void oncreate(){

        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

}
