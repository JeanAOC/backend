package com.example.demo.service;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public String authenticate(AuthenticationRequest authenticationRequest) {
        // Lógica de autenticación (validar usuario y contraseña)
        // Retorna un token JWT
        return "token_jwt_generado";
    }

    public String register(RegisterRequest registerRequest) {
        // Lógica de registro (crear un nuevo usuario)
        // Retorna un token JWT
        return "token_jwt_generado";
    }
}