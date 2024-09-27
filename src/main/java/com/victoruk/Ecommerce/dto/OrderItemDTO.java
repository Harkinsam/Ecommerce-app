package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.victoruk.Ecommerce.entity.OrderItemStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDTO {


    private Long id;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discountApplied;
    private OrderItemStatus orderStatus;
    private LocalDate createdAt;
    private LocalDate updatedAt;


    private OrderDTO order;
    private ProductDTO product;
}
