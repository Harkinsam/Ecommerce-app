package com.victoruk.Ecommerce.services.impli;

import com.victoruk.Ecommerce.Exception.MyException;
import com.victoruk.Ecommerce.Mapper.Mapper;
import com.victoruk.Ecommerce.dto.CartDTO;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Cart;
import com.victoruk.Ecommerce.entity.User;
import com.victoruk.Ecommerce.repository.CartRepository;
import com.victoruk.Ecommerce.repository.UserRepository;
import com.victoruk.Ecommerce.services.interfac.CartInterface;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartService implements CartInterface {


        private final CartRepository cartRepository;
        private final UserRepository userRepository;

        public CartService(CartRepository cartRepository, UserRepository userRepository) {
            this.cartRepository = cartRepository;
            this.userRepository = userRepository;
        }

        @Override
        public Response createCart(Long userId) {
            Response response = new Response();

            try {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new MyException("User not found: " + userId));

                Cart cart = new Cart();
                cart.setUser(user);
                cart.setTotalPrice(BigDecimal.ZERO);
                Cart savedCart = cartRepository.save(cart);
                CartDTO cartDTO = Mapper.mapCartEntityToCartDTO(savedCart);

                response.setStatusCode(200);
                response.setMessage("Success");
                response.setData("Created Cart", cartDTO);
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("User not found: " + userId);
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response updateCart(Long cartId) {
            Response response = new Response();

            try {
                Cart cart = cartRepository.findById(cartId)
                        .orElseThrow(() -> new MyException("Cart not found: " + cartId));

                cart.calculateTotalPrice();
                Cart updatedCart = cartRepository.save(cart);
                CartDTO cartDTO = Mapper.mapCartEntityToCartDTO(updatedCart);

                response.setStatusCode(200);
                response.setMessage("Success");
                response.setData("Updated Cart", cartDTO);
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Cart not found: " + cartId);
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response getCartById(Long cartId) {
            Response response = new Response();

            try {
                Cart cart = cartRepository.findById(cartId)
                        .orElseThrow(() -> new MyException("Cart not found : " + cartId));

                CartDTO cartDTO = Mapper.mapCartEntityToCartDTO(cart);

                response.setStatusCode(200);
                response.setMessage("Success");
                response.setData("Cart", cartDTO);
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Cart not found for user: " + cartId);
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response deleteCart(Long cartId) {
            Response response = new Response();

            try {
                Cart cart = cartRepository.findById(cartId)
                        .orElseThrow(() -> new MyException("Cart not found: " + cartId));

                cartRepository.delete(cart);

                response.setStatusCode(200);
                response.setMessage("Cart deleted successfully.");
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Cart not found: " + cartId);
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred: " + e.getMessage());
            }

            return response;
        }

        @Override
        public Response clearCart(Long cartId) {
            Response response = new Response();

            try {
                Cart cart = cartRepository.findById(cartId)
                        .orElseThrow(() -> new MyException("Cart not found: " + cartId));

                cart.getCartItems().clear();
                cart.setTotalPrice(BigDecimal.ZERO);
                Cart clearedCart = cartRepository.save(cart);
                CartDTO cartDTO = Mapper.mapCartEntityToCartDTO(clearedCart);

                response.setStatusCode(200);
                response.setMessage("Cart cleared successfully.");
                response.setData("Cleared Cart", cartDTO);
            } catch (MyException e) {
                response.setStatusCode(400);
                response.setMessage("Cart not found: " + cartId);
            } catch (Exception e) {
                response.setStatusCode(500);
                response.setMessage("An error occurred: " + e.getMessage());
            }

            return response;
        }


}
