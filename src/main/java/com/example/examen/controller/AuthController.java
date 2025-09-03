package com.example.examen.controller;

import com.example.examen.dto.AuthResponse;
import com.example.examen.dto.LoginRequest;
import com.example.examen.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        // El token llega como "Bearer <token>", necesitamos limpiarlo.
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            authService.logout(jwt);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
