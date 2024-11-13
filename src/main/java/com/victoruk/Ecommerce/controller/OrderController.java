package com.victoruk.Ecommerce.controller;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.OrderStatus;
import com.victoruk.Ecommerce.services.interfac.OrderInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderInterface orderService;

    public OrderController(OrderInterface orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create/{userId}/{orderConfirmationCode}")
    public ResponseEntity<Response> createOrderFromCart(@PathVariable Long userId, @PathVariable String orderConfirmationCode) {
        Response response = orderService.createOrderFromCart(userId, orderConfirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<Response> updateOrder(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        Response response = orderService.updateOrder(orderId, status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Response> getOrderById(@PathVariable Long orderId) {
        Response response = orderService.getOrderById(orderId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Response> deleteOrder(@PathVariable Long orderId) {
        Response response = orderService.deleteOrder(orderId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/clear-items/{orderId}")
    public ResponseEntity<Response> clearItemsFromOrder(@PathVariable Long orderId) {
        Response response = orderService.clearItemsFromOrder(orderId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
