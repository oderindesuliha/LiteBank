package org.peejay.joblync.controller;

import org.peejay.joblync.dtos.requests.UserLoginRequest;
import org.peejay.joblync.dtos.requests.UserRegisterRequest;
import org.peejay.joblync.dtos.responses.JwtResponse;
import org.peejay.joblync.dtos.responses.UserRegisterResponse;
import org.peejay.joblync.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            UserRegisterResponse response = userService.registerUser(userRegisterRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            UserRegisterResponse errorResponse = new UserRegisterResponse();
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        try {
            JwtResponse response = userService.loginUser(userLoginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            JwtResponse errorResponse = new JwtResponse();
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}