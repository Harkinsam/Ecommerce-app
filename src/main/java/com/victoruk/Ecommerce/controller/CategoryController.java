package com.victoruk.Ecommerce.controller;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Category;
import com.victoruk.Ecommerce.services.interfac.CategoryInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryInterface categoryService;

    public CategoryController(CategoryInterface categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Response> addCategory(@RequestBody Category category) {
        Response response = categoryService.addCategory(category);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Response> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody Category category) {
        Response response = categoryService.updateCategory(categoryId, category);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Response> deleteCategory(@PathVariable Long categoryId) {
        Response response = categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Response> getCategoryById(@PathVariable Long categoryId) {
        Response response = categoryService.getCategoryById(categoryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllCategories() {
        Response response = categoryService.getAllCategories();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchCategoryByName(@RequestParam String name) {
        Response response = categoryService.searchCategoryByName(name);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<Response> getProductsByCategory(@PathVariable Long categoryId) {
        Response response = categoryService.getProductsByCategory(categoryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Response> addProductToCategory(
            @PathVariable Long categoryId,
            @PathVariable Long productId) {
        Response response = categoryService.addProductToCategory(categoryId, productId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Response> removeProductFromCategory(
            @PathVariable Long categoryId,
            @PathVariable Long productId) {
        Response response = categoryService.removeProductFromCategory(categoryId, productId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
