package com.victoruk.Ecommerce.repository;


import com.victoruk.Ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);


    Optional<User> findByUserName(String username);


}