package com.spring3.oauth.jwt.controllers;

import com.spring3.oauth.jwt.dtos.*;

import com.spring3.oauth.jwt.services.JwtService;

import com.spring3.oauth.jwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private  AuthenticationManager authenticationManager;
    
   
   // Public Access
   @GetMapping("/hello")
   public String hello() {
   try {
       return "Hello from a public endpoint...";
   } catch (Exception e){
       throw new RuntimeException(e);
   }
  }
    
    // Public Access
     @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
           
           String uName = authRequestDTO.getUsername();

           return JwtResponseDTO.builder()

                   // Sending the JWT AccessToken to the client for AUTH in future requests
                   .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))

                   // Sending username to client for a welcome greeting when logged in
                   .userName( uName )
                   .build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
            
        }
    } 
    
    // Public Access
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/save")
    public ResponseEntity saveUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.saveUser(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
       
    // It is protected by Auth
    @GetMapping("/ping")
    public String ping() {
    try {
        return "Welcome to a protected ping endpoint";
    } catch (Exception e){
        throw new RuntimeException(e);
    }
   }
    
    // It is protected by Auth and by Role
    // Note: If the User have the role USER instead of ADMIN there will be a 403 forbidden
    @SuppressWarnings("rawtypes")
   // @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        try {
            List<UserResponse> userResponses = userService.getAllUser();
            return ResponseEntity.ok(userResponses);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    // It is protected by Auth
    @PostMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        try {
        UserResponse userResponse = userService.getUser();
        return ResponseEntity.ok().body(userResponse);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    

}
