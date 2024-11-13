package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {


    private Long id;
    private String Street;
    private String city;
    private String state;
    private String country;

    private UserDTO user;
    private OrderDTO order;
}
