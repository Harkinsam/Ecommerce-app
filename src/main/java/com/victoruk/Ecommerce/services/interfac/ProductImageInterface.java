package com.victoruk.Ecommerce.services.interfac;


import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

public interface ProductImageInterface {




//    Response createProductImage(String altText, MultipartFile file);

    Response createProductImage(Long productId, String altText, MultipartFile photo);

    Response updateProductImage(Long id, MultipartFile newImageFile, String altText);

    Response deleteProductImage(Long id);


    Response getProductImageById(Long id);


    Response getAllProductImage();

}
