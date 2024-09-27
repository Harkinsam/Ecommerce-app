package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDamageDTO {

    private Long id;
    private int quantityDamaged;
    private LocalDate damageDate;
    private String reason;
    private String photoUrl;

    private ProductDTO product;

}
