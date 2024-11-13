package com.victoruk.Ecommerce.services.interfac;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.CartItem;

public interface CartItemInterface {

    Response createCartItem(CartItem cartItem);

    Response updateCartItem(Long cartItemId, Integer quantity);

    Response getCartItemById(Long cartItemId);

    Response removeCartItem(Long cartItemId);

    Response getAllCartItemsByCartId(Long cartId);

}
