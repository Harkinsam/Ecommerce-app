package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "delivery_addresses")
public class Address {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "address_line1")
        private String addressLine1;

        @Column(name = "city")
        private String city;

        @Column(name = "state")
        private String state;

        @Column(name = "country")
        private String country;

        @Column(name = "phone_number")
        private String phoneNumber;

        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        private User user;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "order_id")
        private Order order;



//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "order_id")
//        private Order order;
//
//        @ManyToOne(fetch = FetchType.EAGER)
//        @JoinColumn(name = "user_id")
//        private User user;

    }

