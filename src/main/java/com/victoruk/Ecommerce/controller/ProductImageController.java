package com.victoruk.Ecommerce.controller;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.ProductImage;
import com.victoruk.Ecommerce.services.interfac.ProductImageInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/product-images")
public class ProductImageController {

    private final ProductImageInterface productImageService;

    public ProductImageController(ProductImageInterface productImageService) {
        this.productImageService = productImageService;
    }

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createProductImage(

            @RequestParam  Long productId,
            @RequestParam  String altText,
            @RequestParam MultipartFile photo){

        Response response = productImageService.createProductImage(productId, altText,  photo);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProductImage(@PathVariable Long id, @RequestPart MultipartFile photo, @RequestParam String altText) {
        Response response = productImageService.updateProductImage(id, photo, altText);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteProductImage(@PathVariable Long id) {
        Response response = productImageService.deleteProductImage(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProductImageById(@PathVariable Long id) {
        Response response = productImageService.getProductImageById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAllProductImages() {
        Response response = productImageService.getAllProductImage();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
