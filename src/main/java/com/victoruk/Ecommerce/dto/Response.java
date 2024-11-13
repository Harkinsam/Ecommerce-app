package com.victoruk.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.victoruk.Ecommerce.entity.Role;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;
    private String message;
    private String token;

    private Role role;

    private String expirationTime;
    private String orderConfirmationCode;

    // Dynamic payload field for both single DTOs and lists of DTOs
    private Map<String, Object> data;

    public void setData(String key, Object value) {
        if (this.data == null) {
            this.data = new HashMap<>(); // Initialize the map if it's null
        }
        this.data.put(key, value);
    }


}
