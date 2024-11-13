package com.victoruk.Ecommerce.services.interfac;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.OrderItem;

public interface OrderItermInterface {


    Response createOrderFromCart(OrderItem orderItem);

    Response updateOrderItem(Long orderItemId, Integer quantity);

    Response getOrderItemById(Long orderItemId);

    Response removeOrderItem(Long orderItemId);

    Response getAllOrderItemsByOrderId(Long orderId);
}
