package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDamageDTO {

    private Long id;
    private String quantityDamaged;
    private LocalDateTime damageDate;
    private String reason;
    private String photoUrl;

    private ProductDTO product;

}
