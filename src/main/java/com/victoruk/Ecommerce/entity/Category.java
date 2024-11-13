package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @PrePersist
    private void onCreate(){

        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        updatedAt = createdAt;
    }


    @PreUpdate
    private void onUpdate(){

        updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }
}
