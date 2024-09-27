package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.victoruk.Ecommerce.entity.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserDTO {

    private Long id;
    private String userName;
    private String email;
    private String phoneNumber;
    private Role role;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private CartDTO cart;
    private List<OrderDTO> orders = new ArrayList<>();
    private AddressDTO address;
}
