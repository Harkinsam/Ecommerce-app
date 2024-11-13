package com.victoruk.Ecommerce.controller;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.services.interfac.ProductDamageInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/damaged-products")
public class ProductDamageController {

    private final ProductDamageInterface productDamageService;

    public ProductDamageController(ProductDamageInterface productDamageService) {
        this.productDamageService = productDamageService;
    }


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addDamagedProduct(
            @RequestParam String quantityDamaged,
            @RequestParam String reason,
            @RequestParam MultipartFile photo) {

        Response response = productDamageService.addDamagedProduct(quantityDamaged, reason, photo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateDamagedProduct(
            @PathVariable Long id,
            @RequestParam String quantityDamaged,
            @RequestParam LocalDate damageDate,
            @RequestParam(required = false) String reason,
            @RequestParam(required = false) MultipartFile photo) {

        Response response = productDamageService.updateDamagedProduct(id, quantityDamaged, damageDate, reason, photo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getDamagedProductById(@PathVariable Long id) {
        Response response = productDamageService.getDamagedProductById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAllDamagedProducts() {
        Response response = productDamageService.getAllDamagedProduct();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
