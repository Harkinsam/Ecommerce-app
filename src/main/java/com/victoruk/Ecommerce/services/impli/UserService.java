package com.victoruk.Ecommerce.services.impli;

import com.victoruk.Ecommerce.Exception.MyException;
import com.victoruk.Ecommerce.Jwt_securityconfig.JWTUtils;
import com.victoruk.Ecommerce.Mapper.Mapper;
import com.victoruk.Ecommerce.dto.LoginRequest;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.dto.UserDTO;
import com.victoruk.Ecommerce.entity.Role;
import com.victoruk.Ecommerce.entity.User;
import com.victoruk.Ecommerce.repository.UserRepository;
import com.victoruk.Ecommerce.services.interfac.Userinterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements Userinterface {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public Response registerUser(User user) {
        Response response = new Response();
        try {
            if (user.getRole() == null) {
                user.setRole(Role.USER);
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                throw new MyException("Password cannot be null or empty");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new MyException(user.getEmail() + " Already Exists");
            }
            // Encode the password only when it's non-null
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);

            UserDTO userDTO = Mapper.mapUserEntityToUserDTO(savedUser);
            response.setStatusCode(200);
            response.setData("User registered", userDTO);
        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Registration: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {

        Response response = new Response();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new MyException("user Not found"));

            var token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");
            response.setMessage("successful");

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error Occurred During USer Login " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUser() {
        Response response = new Response();

        try {
            List<User> userList = userRepository.findAll();

            List<UserDTO> userDTOList = Mapper.userListEntityTouserDTOList(userList);

            response.setMessage("successful");
            response.setStatusCode(200);
            response.setData("user", userDTOList);

        } catch (Exception e) {
            response.setMessage("Error fetching users: " + e.getMessage());
            response.setStatusCode(500);
        }

        return response;
    }

    @Override
    public Response getUserById(Long userId) {

        Response response = new Response();
        try {

            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new MyException("user ID not found " + userId));
            var userdto = Mapper.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setData("User", userdto);
        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage("Unable to get user: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("An error occurred: " + e.getMessage());
        }
        return response;
    }


    @Override
    public Response getUserOrderHistory(Long userId) {
        Response response = new Response();
        try {
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new MyException("user iD not found: " + userId));
            var userDTO = Mapper.mapUserEntityToUserDTOPlusOder(user);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setData("User order history", userDTO);
        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage("Unable to get user order history " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("An error occured please try again : " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();
        try {

            var user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new MyException("user ID not found " + email));
            var userdto = Mapper.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setData("User info", userdto);

        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage("Unable to get user info: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("An error occured please try again: " + e.getMessage());
        }
        return response;
    }
}