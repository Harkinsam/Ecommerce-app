package com.victoruk.Ecommerce.utils;
import com.victoruk.Ecommerce.dto.*;
import com.victoruk.Ecommerce.entity.*;


import java.util.stream.Collectors;

public class Utils {


    public static UserDTO mapUserEntityToUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        return userDTO;
    }




    public static UserDTO mapUserEntityToUserDTOPlusCart(User user){

        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        if (user.getCart() != null){

            userDTO.setCart(Utils.mapCartEntityToCartDTO(user.getCart()));
        }

        return userDTO;
    }

    public static UserDTO mapUserEntityToUserDTOPlusOders(User user) {

        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        userDTO.setCart(Utils.mapCartEntityToCartDTO(user.getCart()));
        userDTO.setAddress(Utils.mapAddressEntityToAddressDTO(user.getAddress()));

        if (user.getOrders() != null )

            userDTO.setOrders(user.getOrders().stream().map(order -> Utils.mapOrderEntityToOrderDTOplusOrderitem(order)).collect(Collectors.toList()));
        return userDTO;

    }

    private static OrderDTO mapOrderEntityToOrderDTOplusOrderitem(Order order) {

        if (order == null) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(order.getId());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setPaymentStatus(order.getPaymentStatus());
        orderDTO.setUser(Utils.mapUserEntityToUserDTO(order.getUser()));
        orderDTO.setAddress(Utils.mapAddressEntityToAddressDTO(order.getAddress()));

        if (order.getOrderItems() != null){

            orderDTO.setOrderItems(order.getOrderItems().stream().map(orderItem ->
                    Utils.mapOrderItemEntityToOrderItemDTO(orderItem)).collect(Collectors.toList()));
        }

            return orderDTO;
    }

    private static OrderItemDTO mapOrderItemEntityToOrderItemDTO(OrderItem orderItem) {

        if (orderItem == null) {
            return null;
        }

        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setOrderStatus(orderItem.getOrderStatus());
        orderItemDTO.setProduct(Utils.mapProductEntityToProductDto(orderItem.getProduct()));

        return orderItemDTO;
    }

    private static ProductDTO mapProductEntityToProductDto(Product product) {

        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setExpiryDate(product.getExpiryDate());
        productDTO.setDiscountDTO(Utils.mapDiscountEntityToDiscountDTO(product.getDiscount()));


        if (product.getProductImages() !=null){
            productDTO.setProductImages(product.getProductImages().stream()
                    .map(productImage -> Utils.mapProductImageEntityToProductImageDTo(productImage)).collect(Collectors.toList()));
        }


        return productDTO;
    }

    private static ProductDTO mapProductEntityToProductDtoPlusDamage(Product product) {

        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setName(productDTO.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setExpiryDate(product.getExpiryDate());
        if (product.getProductDamages() != null) {

            productDTO.setProductDamages(product.getProductDamages().stream()
                    .map(productDamage -> Utils.mapProductDamagesEntityToProductDamagesDTo(productDamage)).collect(Collectors.toList()));
        }
        return productDTO;
    }

    private static DiscountDTO mapDiscountEntityToDiscountDTO(Discount discount){

        if (discount == null) {
            return null;
        }

        DiscountDTO discountDTO = new DiscountDTO();

        discountDTO.setId(discount.getId());
        discountDTO.setDiscountPercentage(discount.getDiscountPercentage());
        return discountDTO;
    }

    private static ProductDamageDTO mapProductDamagesEntityToProductDamagesDTo(ProductDamage productDamage) {

        if (productDamage == null) {
            return null;
        }

       ProductDamageDTO productDamageDTO = new ProductDamageDTO();

        productDamageDTO.setId(productDamage.getId());
        productDamageDTO.setQuantityDamaged(productDamage.getQuantityDamaged());
        productDamageDTO.setDamageDate(productDamage.getDamageDate());
        productDamageDTO.setReason(productDamage.getReason());
        productDamageDTO.setPhotoUrl(productDamage.getPhotoUrl());

        return productDamageDTO;
    }

    private static ProductImageDTO mapProductImageEntityToProductImageDTo(ProductImage productImage) {

        if (productImage == null) {
            return null;
        }

        ProductImageDTO productImageDTO = new ProductImageDTO();

        productImageDTO.setId(productImage.getId());
        productImageDTO.setImageUrl(productImage.getImageUrl());
        productImageDTO.setAltText(productImage.getAltText());


        return productImageDTO;
    }


    private static AddressDTO mapAddressEntityToAddressDTO(Address address) {

        if (address == null) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setAddressLine1(address.getAddressLine1());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setPhoneNumber(address.getPhoneNumber());

        return addressDTO;
    }

    public static CartDTO mapCartEntityToCartDTO(Cart cart) {

        if (cart == null) {
            return null;
        }

        CartDTO cartDTO = new CartDTO();

        cartDTO.setId(cart.getId());
        cartDTO.setTotalPrice(cart.getTotalPrice());

        if (!cart.getCartItems().isEmpty()) {

            cartDTO.setCartItems(cart.getCartItems().stream().map(cartItem ->
                    Utils.mapCartItemEntityToCartItemDTO(cartItem)).collect(Collectors.toList()));


        }
        return cartDTO;
    }

    public static CartItemDTO mapCartItemEntityToCartItemDTO(CartItem cartItem) {

        if (cartItem == null) {
            return null;
        }
        CartItemDTO cartItemDTO = new CartItemDTO();

        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setPrice(cartItem.getPrice());
        cartItemDTO.setProduct(Utils.mapProductEntityToProductDto(cartItem.getProduct()));
        return cartItemDTO;
    }
}