package com.victoruk.Ecommerce.controller;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Product;
import com.victoruk.Ecommerce.services.interfac.ProductInterface;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductInterface productService;

    public ProductController(ProductInterface productService) {
        this.productService = productService;
    }

    @PostMapping("/addproduct")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addProduct(@RequestBody Product product) {
        Response response = productService.addProduct(product);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProduct(@PathVariable Long id,
                                                  @RequestParam  (required = false)String name,
                                                  @RequestParam (required = false) String description,
                                                  @RequestParam (required = false) BigDecimal price,
                                                  @RequestParam (required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate expiryDate ) {

        Response response = productService.updateProduct(id,name, description, price, expiryDate);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAllProducts() {
        Response response = productService.getAllProduct();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<Response> getProductById(@PathVariable Long productId) {
        Response response = productService.getProductById(productId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/expiring")
    public ResponseEntity<Response> getExpiringProducts() {
        Response response = productService.getProductByExpiringDate();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long productId) {
        Response response = productService.deleteProduct(productId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


}
