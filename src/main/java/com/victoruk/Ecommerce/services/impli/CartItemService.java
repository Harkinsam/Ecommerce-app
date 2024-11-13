package com.victoruk.Ecommerce.services.impli;


import com.victoruk.Ecommerce.Exception.MyException;
import com.victoruk.Ecommerce.Mapper.Mapper;
import com.victoruk.Ecommerce.dto.CartItemDTO;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.CartItem;
import com.victoruk.Ecommerce.repository.CartItemRepository;
import com.victoruk.Ecommerce.services.interfac.CartItemInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService implements CartItemInterface {


        private final CartItemRepository cartItemRepository;

        public CartItemService(CartItemRepository cartItemRepository) {
            this.cartItemRepository = cartItemRepository;
        }

        @Override
        public Response createCartItem(CartItem cartItem) {
            Response response = new Response();

            try {
                cartItem.calculatePrice(); // Calculate price based on quantity and product price
                CartItem savedCartItem = cartItemRepository.save(cartItem);
                CartItemDTO cartItemDTO = Mapper.mapCartItemEntityToCartItemDTO(savedCartItem);
                response.setStatusCode(200);
                response.setMessage("Success");
                response.setData("Created Cart Item", cartItemDTO);
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Invalid credential");
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred, please try again: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response updateCartItem(Long cartItemId, Integer quantity) {
            Response response = new Response();

            try {
                // Validate quantity before proceeding
                if (quantity == null || quantity <= 0) {
                    throw new MyException("Invalid quantity: " + quantity);
                }

                CartItem cartItem = cartItemRepository.findById(cartItemId)
                        .orElseThrow(() -> new MyException("CartItem not found: " + cartItemId));

                cartItem.setQuantity(quantity);
                cartItem.calculatePrice(); // Recalculate price after updating quantity
                CartItem updatedCartItem = cartItemRepository.save(cartItem);
                CartItemDTO cartItemDTO = Mapper.mapCartItemEntityToCartItemDTO(updatedCartItem);
                response.setStatusCode(200);
                response.setMessage("Success");
                response.setData("Updated Cart Item", cartItemDTO);
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Invalid credential: " + e.getMessage());
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred, please try again: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response getCartItemById(Long cartItemId) {
            Response response = new Response();

            try {
                CartItem cartItem = cartItemRepository.findById(cartItemId)
                        .orElseThrow(() -> new MyException("CartItem not found: " + cartItemId));
                CartItemDTO cartItemDTO = Mapper.mapCartItemEntityToCartItemDTO(cartItem);
                response.setStatusCode(200);
                response.setMessage("Success");
                response.setData("Cart Item", cartItemDTO);
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Invalid credential: " + e.getMessage());
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred, please try again: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response removeCartItem(Long cartItemId) {
            Response response = new Response();

            try {
                CartItem cartItem = cartItemRepository.findById(cartItemId)
                        .orElseThrow(() -> new MyException("CartItem not found: " + cartItemId));
                cartItemRepository.delete(cartItem);
                response.setStatusCode(200);
                response.setMessage("Cart Item deleted successfully");
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Invalid credential: " + e.getMessage());
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred, please try again: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response getAllCartItemsByCartId(Long cartId) {
            Response response = new Response();

            try {
                List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
                List<CartItemDTO> cartItemDTOs = cartItems.stream()
                        .map(Mapper::mapCartItemEntityToCartItemDTO)
                        .collect(Collectors.toList());
                response.setStatusCode(200);
                response.setMessage("Success");
                response.setData("Cart Items", cartItemDTOs);
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred, please try again: " + e.getMessage());
            }

            return response;
        }


}
