package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemDTO {

    private Long id;
    private int quantity;
    private BigDecimal price;

    private ProductDTO product;
}
