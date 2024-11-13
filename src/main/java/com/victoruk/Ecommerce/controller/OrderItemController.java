package com.victoruk.Ecommerce.controller;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.OrderItem;
import com.victoruk.Ecommerce.services.interfac.OrderItermInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItermInterface orderItemService;

    public OrderItemController(OrderItermInterface orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public ResponseEntity<Response> createOrderItem( @RequestBody OrderItem orderItem) {
        Response response = orderItemService.createOrderFromCart(orderItem);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateOrderItem(@PathVariable Long id, @RequestParam Integer quantity) {
        Response response = orderItemService.updateOrderItem(id, quantity);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getOrderItemById(@PathVariable Long id) {
        Response response = orderItemService.getOrderItemById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> removeOrderItem(@PathVariable Long id) {
        Response response = orderItemService.removeOrderItem(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Response> getAllOrderItemsByOrderId(@PathVariable Long orderId) {
        Response response = orderItemService.getAllOrderItemsByOrderId(orderId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}
