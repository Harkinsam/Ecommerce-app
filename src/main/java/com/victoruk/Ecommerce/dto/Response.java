package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.victoruk.Ecommerce.entity.Role;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;
    private String message;

    // Authentication-related fields
    private String token;

    private Role role;

    private String expirationTime;
    private String orderConfirmationCode;

    // Dynamic payload field for both single DTOs and lists of DTOs
    private Map<String, Object> data;

//
//    public int getStatusCode() {
//        return statusCode;
//    }
//
//    public void setStatusCode(int statusCode) {
//        this.statusCode = statusCode;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public String getExpirationTime() {
//        return expirationTime;
//    }
//
//    public void setExpirationTime(String expirationTime) {
//        this.expirationTime = expirationTime;
//    }
//
//    public String getOrderConfirmationCode() {
//        return orderConfirmationCode;
//    }
//
//    public void setOrderConfirmationCode(String orderConfirmationCode) {
//        this.orderConfirmationCode = orderConfirmationCode;
//    }
//
//    public Map<String, Object> getData() {
//        return data;
//    }
//
//    public void setData(Map<String, Object> data) {
//        this.data = data;
//    }
}
