package com.victoruk.Ecommerce.Jwt_securityconfig;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

}
