package com.victoruk.Ecommerce.repository;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findByExpiryDateLessThanEqual(LocalDate date);

}
