package com.victoruk.Ecommerce.controller;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.CartItem;
import com.victoruk.Ecommerce.services.interfac.CartItemInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemInterface cartItemService;

    @Autowired
    public CartItemController(CartItemInterface cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/create")
    public Response createCartItem(@RequestBody CartItem cartItem) {
        return cartItemService.createCartItem(cartItem);
    }

    @PutMapping("/update/{id}")
    public Response updateCartItem(@PathVariable("id") Long cartItemId, @RequestParam Integer quantity) {
        return cartItemService.updateCartItem(cartItemId, quantity);
    }

    @GetMapping("/{id}")
    public Response getCartItemById(@PathVariable("id") Long cartItemId) {
        return cartItemService.getCartItemById(cartItemId);
    }

    @DeleteMapping("/delete/{id}")
    public Response removeCartItem(@PathVariable("id") Long cartItemId) {
        return cartItemService.removeCartItem(cartItemId);
    }

    @GetMapping("/cart/{cartId}")
    public Response getAllCartItemsByCartId(@PathVariable("cartId") Long cartId) {
        return cartItemService.getAllCartItemsByCartId(cartId);
    }
}
