package com.victoruk.Ecommerce.services.interfac;

import com.victoruk.Ecommerce.dto.Response;

public interface CartInterface {


    Response createCart(Long userId);

    Response updateCart(Long cartId);

    Response getCartById(Long userId);

    Response deleteCart(Long cartId);

    Response clearCart(Long cartId);
}
