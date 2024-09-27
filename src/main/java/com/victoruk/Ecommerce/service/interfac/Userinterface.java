package com.victoruk.Ecommerce.service.interfac;


import com.victoruk.Ecommerce.dto.LoginRequest;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.User;

public interface Userinterface {

    Response registerUser (User user);

    Response  login(LoginRequest loginRequest);

//    Response getAllUsers();

    Response getUserById(String userId);

    Response getUserOrderHistory(String userId);

    Response getMyInfo( String email);
}
