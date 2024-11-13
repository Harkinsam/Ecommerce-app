package com.victoruk.Ecommerce.controller;

import com.victoruk.Ecommerce.dto.LoginRequest;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.User;
import com.victoruk.Ecommerce.services.interfac.Userinterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final Userinterface userService;

    @Autowired
    public UserController(Userinterface userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody User user) {
        Response response = userService.registerUser(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        Response response = userService.getAllUser();
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {
        Response response = userService.getUserById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // this end point allows only a user to see order history / personal info.

    @GetMapping("/{id}/order-history")
    public ResponseEntity<Response> getUserOrderHistory(@PathVariable Long id) {
        Response response = userService.getUserOrderHistory(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


//    @GetMapping("/myinfo")
//    public ResponseEntity<Response> getMyInfo(@RequestParam String email) {
//        Response response = userService.getMyInfo(email);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
}
