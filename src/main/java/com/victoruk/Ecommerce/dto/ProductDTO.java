package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.victoruk.Ecommerce.entity.Discount;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDate expiryDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private DiscountDTO discountDTO;
//    private List<ProductImageDTO> productImages = new ArrayList<>();
//    private List<ProductDamageDTO> productDamages = new ArrayList<>();

    private List<ProductImageDTO> productImages = new ArrayList<>();

    private List<ProductDamageDTO> productDamages = new ArrayList<>();

}
