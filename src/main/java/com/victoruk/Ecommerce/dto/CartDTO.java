package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDTO {

    private Long id;
    private BigDecimal totalPrice;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private UserDTO user;
    private List<CartItemDTO> cartItems = new ArrayList<>();
}
