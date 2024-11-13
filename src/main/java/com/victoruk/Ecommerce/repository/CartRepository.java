package com.victoruk.Ecommerce.repository;

import com.victoruk.Ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

//    Optional<Cart> findById(Long userId);


    Optional findByUserId(Long userId);
}
