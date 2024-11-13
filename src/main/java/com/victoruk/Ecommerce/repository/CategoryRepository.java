package com.victoruk.Ecommerce.repository;

import com.victoruk.Ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {


    List<Category> findByNameContainingIgnoreCase(String name);
}
