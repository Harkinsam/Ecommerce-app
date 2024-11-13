package com.victoruk.Ecommerce.services.impli;

import com.victoruk.Ecommerce.Exception.MyException;
import com.victoruk.Ecommerce.Mapper.Mapper;
import com.victoruk.Ecommerce.dto.OrderDTO;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.*;
import com.victoruk.Ecommerce.repository.CartRepository;
import com.victoruk.Ecommerce.repository.OrderItemRepository;
import com.victoruk.Ecommerce.repository.OrderRepository;
import com.victoruk.Ecommerce.repository.UserRepository;
import com.victoruk.Ecommerce.services.interfac.OrderInterface;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements OrderInterface {


        private final OrderRepository orderRepository;
        private final UserRepository userRepository;
        private final CartRepository cartRepository;
        private final OrderItemRepository orderItemRepository;

        public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository, OrderItemRepository orderItemRepository) {
            this.orderRepository = orderRepository;
            this.userRepository = userRepository;
            this.cartRepository = cartRepository;
            this.orderItemRepository = orderItemRepository;
        }




    @Transactional
    @Override
    public Response createOrderFromCart(Long userId, String orderConfirmationCode) {
        Response response = new Response();
        try {
            // Fetch user
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new MyException("User not found: " + userId));

            // Fetch cart by userId
            Cart cart = cartRepository.findById(userId)
                    .orElseThrow(() -> new MyException("Cart not found for user: " + userId));

            // Check if cart is empty
            BigDecimal totalAmount = cart.getTotalPrice();
            if (totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new MyException("Cart is empty");
            }

            // Create a new Order
            Order order = new Order();
            order.setUser(user);
            order.setTotalAmount(totalAmount);
            order.setOrderConfirmationCode(orderConfirmationCode);
            order.setOrderStatus(OrderStatus.PENDING);

            // Convert CartItems to OrderItems
            List<OrderItem> orderItems = mapCartItemsToOrderItems(cart.getCartItems());
            order.setOrderItems(orderItems);  // Set the mapped order items

            // Save the order
            orderRepository.save(order);

            // Clear the cart after creating the order
            cart.getCartItems().clear();
            cartRepository.save(cart);

            // Set success response
            response.setStatusCode(200);
            response.setMessage("Order created successfully from cart");

        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("An error occurred: " + e.getMessage());
        }
        return response;
    }

    // Method to map CartItem to OrderItem
    private List<OrderItem> mapCartItemsToOrderItems(List<CartItem> cartItems) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItems.add(orderItem);
        }
        return orderItems;
    }

        @Override
        public Response updateOrder(Long orderId, OrderStatus status) {
                Response response = new Response();

                try {
                    Order order = orderRepository.findById(orderId)
                            .orElseThrow(() -> new MyException("Order not found: " + orderId));

                    order.setOrderStatus(status);
                    order.setUpdatedAt(LocalDateTime.now());

                    Order updatedOrder = orderRepository.save(order);
                    OrderDTO orderDTO = Mapper.mapOrderEntityToOrderDto(updatedOrder);

                    response.setStatusCode(200);
                    response.setMessage("Order updated successfully.");
                    response.setData("Updated Order", orderDTO);
                } catch (MyException e) {
                    response.setStatusCode(400);
                    response.setMessage("Order not found: " + orderId);
                } catch (Exception e) {
                    response.setStatusCode(500);
                    response.setMessage("An error occurred: " + e.getMessage());
                }

                return response;
            }

        @Override
        public Response getOrderById(Long orderId) {
            Response response = new Response();

            try {
                Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new MyException("Order not found: " + orderId));

                OrderDTO orderDTO = Mapper.mapOrderEntityToOrderDto(order);

                response.setStatusCode(200);
                response.setMessage("Success");
                response.setData("Order", orderDTO);
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Order not found: " + orderId);
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response deleteOrder(Long orderId) {
            Response response = new Response();

            try {
                Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new MyException("Order not found: " + orderId));

                orderRepository.delete(order);

                response.setStatusCode(200);
                response.setMessage("Order deleted successfully.");
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Order not found: " + orderId);
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response clearItemsFromOrder(Long orderId) {
            Response response = new Response();

            try {
                Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new MyException("Order not found: " + orderId));

                order.getOrderItems().clear();
                order.setTotalAmount(BigDecimal.ZERO);
                order.setUpdatedAt(LocalDateTime.now());

                Order clearedOrder = orderRepository.save(order);
                OrderDTO orderDTO = Mapper.mapOrderEntityToOrderDto(clearedOrder);

                response.setStatusCode(200);
                response.setMessage("Order items cleared successfully.");
                response.setData("Cleared Order", orderDTO);
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Order not found: " + orderId);
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred: " + e.getMessage());
            }

            return response;
        }

}
