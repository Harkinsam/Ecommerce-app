package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.victoruk.Ecommerce.entity.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscountDTO {

    private Long id;

    private BigDecimal discountPercentage;

    private LocalDate startDate;

    private LocalDate endDate;

}
