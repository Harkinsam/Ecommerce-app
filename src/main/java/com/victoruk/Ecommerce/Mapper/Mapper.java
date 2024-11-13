package com.victoruk.Ecommerce.Mapper;
import com.victoruk.Ecommerce.dto.*;
import com.victoruk.Ecommerce.entity.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {


    public static UserDTO mapUserEntityToUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static UserDTO mapUserEntityToUserDTOPlusOder(User user){

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());

        userDTO.setOrders(user.getOrders().stream()
                .map(order -> Mapper.mapOrderEntityToOrderDto(order)).collect(Collectors.toList()));

        return userDTO;
    }

    public static OrderDTO mapOrderEntityToOrderDto(Order order){

        if (order == null){
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(order.getId());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setOrderItems(order.getOrderItems().stream()
                .map(orderItem -> Mapper.mapOrderItemEntityToOrderItemDTO(orderItem)).collect(Collectors.toList()));
        orderDTO.setPayment(Mapper.mapPaymentEntityToPaymentDTO(order.getPayment()));
        orderDTO.setAddress(Mapper.mapAddressEntityToAddressDTO(order.getAddress()));

        return orderDTO;
    }

    private static PaymentDTO mapPaymentEntityToPaymentDTO(Payment payment) {

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setMethod(payment.getMethod());
        paymentDTO.setPaymentStatus(payment.getPaymentStatus());
        return paymentDTO;

    }



    public static OrderItemDTO mapOrderItemEntityToOrderItemDTO(OrderItem orderItem) {

        if (orderItem == null) {
            return null;
        }

        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setProduct(Mapper.mapProductEntityToProductDto(orderItem.getProduct()));

        return orderItemDTO;
    }

    public static ProductDTO mapProductEntityToProductDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setExpiryDate(product.getExpiryDate());

        // Check if discount is active and within valid date range
        if (product.getDiscount() != null) {
            Discount discount = product.getDiscount();
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(discount.getStartDate()) && now.isBefore(discount.getEndDate())) {
                BigDecimal discountedPrice = product.getPrice().subtract(
                        product.getPrice().multiply(discount.getDiscountPercentage().divide(new BigDecimal(100)))
                );
                productDTO.setDiscountedPrice(discountedPrice);
            } else {
                productDTO.setDiscountedPrice(product.getPrice());  // No discount applied
            }
        } else {
            productDTO.setDiscountedPrice(product.getPrice()); // No discount available
        }

        return productDTO;
    }


//    public static ProductDTO mapProductEntityToProductDto(Product product) {
//
//        if (product == null) {
//            return null;
//        }
//
//        ProductDTO productDTO = new ProductDTO();
//
//        productDTO.setId(product.getId());
//        productDTO.setName(product.getName());
//        productDTO.setDescription(product.getDescription());
//        productDTO.setPrice(product.getPrice());
//        productDTO.setExpiryDate(product.getExpiryDate());
//
//        if (product.getCategory() != null) {
//            productDTO.setCategoryId(product.getCategory().getId());
//        }
//        if (product.getDiscount() != null) {
//            productDTO.setDiscountId(product.getDiscount().getId());
//            productDTO.setDiscountedPrice(product.getDiscountedPrice()); // Apply discount
//        } else {
//            productDTO.setDiscountedPrice(product.getPrice()); // No discount, use original price
//        }
//
//        return productDTO;
//    }


    private static ProductImageDTO mapProductImageEntityToProductImageDTo(ProductImage productImage) {

        if (productImage == null) {
            return null;
        }

        ProductImageDTO productImageDTO = new ProductImageDTO();

        productImageDTO.setId(productImage.getId());
        productImageDTO.setPhotoUrl(productImage.getImageUrl());
        productImageDTO.setAltText(productImage.getAltText());
        productImageDTO.setProductId(productImage.getProduct().getId());


        return productImageDTO;
    }

//    public static ProductDTO mapProductEntityToProductDtoPlusDiscountAndImage(Product product) {
//
//        if (product == null) {
//            return null;
//        }
//
//        ProductDTO productDTO = new ProductDTO();
//
//        productDTO.setId(product.getId());
//        productDTO.setName(product.getName());
//        productDTO.setDescription(product.getDescription());
//        productDTO.setPrice(product.getPrice());
//        productDTO.setExpiryDate(product.getExpiryDate());
//        productDTO.setDiscount(product.getDiscount());
//
//
//        if (product.getProductImages() !=null){
//            productDTO.setProductImages(product.getProductImages().stream()
//                    .map(productImage -> Mapper.mapProductImageEntityToProductImageDTo(productImage)).collect(Collectors.toList()));
//        }
//
//
//        return productDTO;
//    }


    public static DiscountDTO mapDiscountEntityToDiscountDTO(Discount discount){

        if (discount == null) {
            return null;
        }

        DiscountDTO discountDTO = new DiscountDTO();

        discountDTO.setId(discount.getId());
        discountDTO.setDiscountPercentage(discount.getDiscountPercentage());
        return discountDTO;
    }

    public static AddressDTO mapAddressEntityToAddressDTO(Address address) {

        if (address == null) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setCountry(address.getCountry());

        return addressDTO;
    }


    /// CART ITEM RELATED MAPPING
    public static CartDTO mapCartEntityToCartDTO(Cart cart) {

        if (cart == null) {
            return null;
        }

        CartDTO cartDTO = new CartDTO();

        cartDTO.setId(cart.getId());
        cartDTO.setTotalPrice(cart.getTotalPrice());

        if (!cart.getCartItems().isEmpty()) {

            cartDTO.setCartItems(cart.getCartItems().stream().map(cartItem ->
                    Mapper.mapCartItemEntityToCartItemDTO(cartItem)).collect(Collectors.toList()));


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
        cartItemDTO.setProduct(Mapper.mapProductEntityToProductDto(cartItem.getProduct()));
        return cartItemDTO;
    }


    //////////////////////DAMAGED PRODUCT MAPPING


    // TO GET dAMAGED PRODUCT

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
                    .map(productDamage -> Mapper.mapProductDamageEntityToProductDamageDTo(productDamage))
                    .collect(Collectors.toList()));
        }
        return productDTO;
    }


    public static ProductDamageDTO mapProductDamageEntityToProductDamageDTo(ProductDamage productDamage) {

        if (productDamage == null) {
            return null;
        }

        ProductDamageDTO productDamageDTO = new ProductDamageDTO();

        productDamageDTO.setId(productDamage.getId());
        productDamageDTO.setQuantityDamaged(productDamage.getQuantityDamaged());
        productDamageDTO.setReason(productDamage.getReason());
        productDamageDTO.setPhotoUrl(productDamage.getPhotoUrl());

        return productDamageDTO;
    }


    public static List<UserDTO> userListEntityTouserDTOList(List<User> userList){
        if (userList == null){
            return new ArrayList<>();
        }
       return userList.stream().map(Mapper::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static ProductImageDTO mapProductImageentityToDto(ProductImage productImage) {

        if (productImage == null){

            return null;
        }

        ProductImageDTO productImageDTO = new  ProductImageDTO();

        productImageDTO.setPhotoUrl(productImage.getImageUrl());
        productImageDTO.setAltText(productImage.getAltText());

        return productImageDTO;
    }

    public static CategoryDTO mapCategoryEntityToCategoryDto(Category category) {

        if (category == null){
            return null;
        }
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setName(category.getName());

        return categoryDTO;
    }

    public static CategoryDTO mapCategoryDtoToCategoryEntityPlusProduct(Category category) {

        if (category == null){
            return null;
        }
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setName(category.getName());
        categoryDTO.setProducts(category.getProducts().stream().map( product -> Mapper.mapProductEntityToProductDto(product))
                .collect(Collectors.toList()));
        return categoryDTO;
    }

}