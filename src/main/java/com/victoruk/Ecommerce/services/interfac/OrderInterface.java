package com.victoruk.Ecommerce.services.interfac;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.OrderStatus;

public interface OrderInterface {


//    @Transactional
    Response createOrderFromCart(Long userId, String orderConfirmationCode);

    Response updateOrder(Long orderId, OrderStatus status);

    Response getOrderById(Long orderId);

    Response deleteOrder(Long orderId);

    Response clearItemsFromOrder(Long orderId);
}
