package com.victoruk.Ecommerce.controller;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.services.interfac.CartInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartInterface cartService;

    public CartController(CartInterface cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Response> createCart(@PathVariable Long userId) {
        Response response = cartService.createCart(userId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/update/{cartId}")
    public ResponseEntity<Response> updateCart(@PathVariable Long cartId) {
        Response response = cartService.updateCart(cartId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Response> getCartById(@PathVariable Long cartId) {
        Response response = cartService.getCartById(cartId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<Response> deleteCart(@PathVariable Long cartId) {
        Response response = cartService.deleteCart(cartId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/clear/{cartId}")
    public ResponseEntity<Response> clearCart(@PathVariable Long cartId) {
        Response response = cartService.clearCart(cartId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
