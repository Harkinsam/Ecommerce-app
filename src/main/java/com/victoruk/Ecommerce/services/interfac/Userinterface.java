package com.victoruk.Ecommerce.services.interfac;


import com.victoruk.Ecommerce.dto.LoginRequest;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.User;

public interface Userinterface {

    Response registerUser (User user);

    Response  login(LoginRequest loginRequest);

    Response getAllUser();

    Response getUserById(Long userId);

    Response getUserOrderHistory(Long userId);

    Response getMyInfo( String email);
}
