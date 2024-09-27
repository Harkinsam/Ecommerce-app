package com.victoruk.Ecommerce.repository;

import com.victoruk.Ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
