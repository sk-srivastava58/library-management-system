package com.library.librarymanagement.controller;

import com.library.librarymanagement.dto.LoginRequest;
import com.library.librarymanagement.dto.LoginResponse;
import com.library.librarymanagement.dto.RegisterRequest;
import com.library.librarymanagement.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        String token = authService.login(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
