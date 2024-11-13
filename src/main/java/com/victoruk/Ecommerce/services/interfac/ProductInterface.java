package com.victoruk.Ecommerce.services.interfac;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ProductInterface {


    Response addProduct(Product product);

    Response updateProduct(Long id, String name, String description,
                           BigDecimal price,
                           LocalDate expiryDate);

    Response getAllProduct();

    Response getProductById(Long productId);

    Response getProductByExpiringDate( );

    Response deleteProduct (Long productId);

//    Response getProductDetailsById(Long productId);


}
