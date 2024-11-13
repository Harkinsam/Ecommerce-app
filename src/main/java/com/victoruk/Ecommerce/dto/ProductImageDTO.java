package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.victoruk.Ecommerce.entity.Product;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductImageDTO {

    private Long id;
    private String photoUrl;
    private String altText;
    private Long productId;

}
