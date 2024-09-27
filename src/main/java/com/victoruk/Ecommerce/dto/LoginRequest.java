package com.victoruk.Ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LoginRequest {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String passWord;

    public @NotBlank(message = "Email is required") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") String email) {
        this.email = email;
    }

    public @NotBlank(message = "password is required") String getPassWord() {
        return passWord;
    }

    public void setPassWord(@NotBlank(message = "password is required") String passWord) {
        this.passWord = passWord;
    }

}
