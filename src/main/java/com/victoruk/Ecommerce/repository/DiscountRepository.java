package com.victoruk.Ecommerce.repository;

import com.victoruk.Ecommerce.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
