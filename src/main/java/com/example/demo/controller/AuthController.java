package com.example.demo.controller;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.dto.JwtResponse; 
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.service.AuthService; 
import com.example.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthService authService; // Inyecta el AuthService

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
        @RequestBody AuthenticationRequest authenticationRequest) {

        String token = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(
        @RequestBody RegisterRequest registerRequest) {

        String token = authenticationService.register(registerRequest);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/refresh-token") // Cambia la ruta para evitar conflictos
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        // Validar el refresh token y generar un nuevo JWT
        String newToken = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(new JwtResponse(newToken)); // Usa JwtResponse
    }
}