package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {


    private Long id;
    private String addressLine1;
    private String city;
    private String state;
    private String country;
    private String phoneNumber;

    private UserDTO user;
    private OrderDTO order;
}
