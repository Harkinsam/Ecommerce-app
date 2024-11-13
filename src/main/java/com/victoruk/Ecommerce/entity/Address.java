package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@Table(name = "delivery_addresses")
public class Address {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "address")
        private String street;

        @Column(name = "city")
        private String city;

        @Column(name = "state")
        private String state;

        @Column(name = "country")
        private String country;

        private LocalDateTime createdAt;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "order_id")
        private Order order;

        @PrePersist
        private void onCreate(){

                createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        }

    }

