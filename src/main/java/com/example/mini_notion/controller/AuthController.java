/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mini_notion.controller;

import com.example.mini_notion.model.User;
import com.example.mini_notion.repository.UserRepository;
import com.example.mini_notion.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;


import java.util.Map;
import java.util.Optional;
/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allows your HTML frontend to call these APIs
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    // A temporary inner class to catch the incoming username and password from the frontend
    public static class AuthRequest {
        public String username;
        public String password;
    }

    // 1. API to register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        // Check if the username is already taken
        if (userRepository.findByUsername(request.username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already exists!"));
        }

        // Create and save the new user
        User newUser = new User();
        newUser.setUsername(request.username);
        // Note: For simplicity, we save the raw password. In a real company, you MUST encrypt this using BCrypt!
        newUser.setPassword(request.password);
        newUser.setRole("USER");
        
        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
    }

    // 2. API to login and receive the JWT Token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        // Find the user in the database
        Optional<User> userOptional = userRepository.findByUsername(request.username);

        // Check if user exists AND the password matches
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(request.password)) {
            
            // Success! Forge the digital token using your JwtService
            String token = jwtService.generateToken(request.username);
            
            // Return the token as a JSON object
            return ResponseEntity.ok(Map.of("token", token));
        }

        // If wrong credentials, return 401 Unauthorized
        return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password!"));
    }
}

