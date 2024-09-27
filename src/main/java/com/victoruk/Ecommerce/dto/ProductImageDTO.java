package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductImageDTO {

    private Long id;
    private String imageUrl;
    private String altText;
    private LocalDate createdAt;
    private ProductDTO product;
}
