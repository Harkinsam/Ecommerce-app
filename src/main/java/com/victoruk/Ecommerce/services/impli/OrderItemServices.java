package com.victoruk.Ecommerce.services.impli;

import com.victoruk.Ecommerce.Exception.MyException;
import com.victoruk.Ecommerce.Mapper.Mapper;
import com.victoruk.Ecommerce.dto.OrderItemDTO;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.OrderItem;
import com.victoruk.Ecommerce.repository.OrderItemRepository;
import com.victoruk.Ecommerce.services.interfac.OrderItermInterface;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServices implements OrderItermInterface {


        private final OrderItemRepository orderItemRepository;

    public OrderItemServices(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }


    @Override
    public Response createOrderFromCart(OrderItem orderItem) {
            Response response = new Response();

            try {
                // Assuming price for OrderItem is calculated when created based on the product price and quantity
                orderItem.setPrice(orderItem.getProduct().getPrice().multiply(
                        BigDecimal.valueOf(orderItem.getQuantity())));
                OrderItem savedOrderItem = orderItemRepository.save(orderItem);
                OrderItemDTO orderItemDTO = Mapper.mapOrderItemEntityToOrderItemDTO(savedOrderItem);
                response.setStatusCode(200);
                response.setMessage("Order item created successfully");
                response.setData("Created Order Item", orderItemDTO);
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Invalid credentials: " + e.getMessage());
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response updateOrderItem(Long orderItemId, Integer quantity) {
            Response response = new Response();

            try {
                // Validate quantity before proceeding
                if (quantity == null || quantity <= 0) {
                    throw new MyException("Invalid quantity: " + quantity);
                }

                OrderItem orderItem = orderItemRepository.findById(orderItemId)
                        .orElseThrow(() -> new MyException("OrderItem not found: " + orderItemId));

                orderItem.setQuantity(quantity);
                // Recalculate price after updating quantity
                orderItem.setPrice(orderItem.getProduct().getPrice().multiply(
                        BigDecimal.valueOf(orderItem.getQuantity())));
                OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
                OrderItemDTO orderItemDTO = Mapper.mapOrderItemEntityToOrderItemDTO(updatedOrderItem);
                response.setStatusCode(200);
                response.setMessage("Order item updated successfully");
                response.setData("Updated Order Item", orderItemDTO);
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Invalid credentials: " + e.getMessage());
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response getOrderItemById(Long orderItemId) {
            Response response = new Response();

            try {
                OrderItem orderItem = orderItemRepository.findById(orderItemId)
                        .orElseThrow(() -> new MyException("OrderItem not found: " + orderItemId));
                OrderItemDTO orderItemDTO = Mapper.mapOrderItemEntityToOrderItemDTO(orderItem);
                response.setStatusCode(200);
                response.setMessage("Order item fetched successfully");
                response.setData("Order Item", orderItemDTO);
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Invalid credentials: " + e.getMessage());
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response removeOrderItem(Long orderItemId) {
            Response response = new Response();

            try {
                OrderItem orderItem = orderItemRepository.findById(orderItemId)
                        .orElseThrow(() -> new MyException("OrderItem not found: " + orderItemId));
                orderItemRepository.delete(orderItem);
                response.setStatusCode(200);
                response.setMessage("Order item deleted successfully");
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Invalid credentials: " + e.getMessage());
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response getAllOrderItemsByOrderId(Long orderId) {
            Response response = new Response();

            try {
                List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
                List<OrderItemDTO> orderItemDTOs = orderItems.stream()
                        .map(Mapper::mapOrderItemEntityToOrderItemDTO)
                        .collect(Collectors.toList());
                response.setStatusCode(200);
                response.setMessage("Order items fetched successfully");
                response.setData("Order Items", orderItemDTOs);
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred: " + e.getMessage());
            }

            return response;
        }

}
