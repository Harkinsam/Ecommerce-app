package com.victoruk.Ecommerce.repository;

import com.victoruk.Ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressReository extends JpaRepository<Address,Long> {

}
