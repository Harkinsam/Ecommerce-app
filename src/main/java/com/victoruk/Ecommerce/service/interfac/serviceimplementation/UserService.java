package com.victoruk.Ecommerce.service.interfac.serviceimplementation;

import com.victoruk.Ecommerce.dto.LoginRequest;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.dto.UserDTO;
import com.victoruk.Ecommerce.entity.Role;
import com.victoruk.Ecommerce.entity.User;
import com.victoruk.Ecommerce.jwt.JwtService;
import com.victoruk.Ecommerce.repository.UserRepository;
import com.victoruk.Ecommerce.service.interfac.Userinterface;
import com.victoruk.Ecommerce.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService implements Userinterface {

    private  final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserService( UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Response registerUser(User user) {

        Response response = new Response();

        try {

        if (user.getRole() == null){

            user.setRole(Role.USER);
        }

        if (userRepository.existsByEmail(user.getEmail())) {
                throw new RuntimeException(user.getEmail() + " This User Already Exists");
            }


            user.setPassWord(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
//        UserDTO userDTO = userMapper.toUserDTO(savedUser);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);
        response.setStatusCode(201);
        response.setMessage("User registered successfully.");
        response.setData(Map.of("user", userDTO));

        }catch (IllegalStateException e) {  // Replacing OurException with IllegalStateException

            response.setStatusCode(400);  // Client-side error
            response.setMessage(e.getMessage());

        }catch (Exception e) {  // General exception handling

            response.setStatusCode(500);  // Server-side error
            response.setMessage("An unexpected error occurred. Please try again later.");
        }


        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassWord())
            );

            var user = userRepository.findByUserName(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));


            var token = jwtService.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");
            response.setMessage("successful");

        }catch (IllegalStateException e) {

            response.setStatusCode(400);
            response.setMessage(e.getMessage());

        }catch (Exception e) {

            response.setStatusCode(500);  // Server-side error
            response.setMessage("An unexpected error occurred. Please try again later.");
        }

        return response;
    }

//    @Override
//    public Response getAllUsers() {
//        Response response = new Response();
//
//        try {
//            // Fetch all users
//            var User userList = userRepository.findAll();
//
//            // Map the list of User entities to UserDTOs
////            List<UserDTO> userDTOList = userMapper.toUserDTO(userList);
//
//            List<UserDTO> userDTOList = Utils.mapUserEntityToUserDTO(userList)
//            // Set response data
//            response.setStatusCode(200);
//            response.setMessage("Successfully fetched all users.");
//            response.setData(Map.of("users", userDTOList));
//
//        } catch (IllegalStateException e) {  // Replacing OurException with IllegalStateException
//
//            response.setStatusCode(400);  // Client-side error
//            response.setMessage(e.getMessage());
//
//        }catch (Exception e) {  // General exception handling
//
//            response.setStatusCode(500);  // Server-side error
//            response.setMessage("An unexpected error occurred. Please try again later.");
//        }
//
//        return response;
//    }


    @Override
    public Response getUserById(String userId) {
        return null;
    }

    @Override
    public Response getUserOrderHistory(String userId) {
        return null;
    }

    @Override
    public Response getMyInfo(String email) {
        return null;
    }
}
