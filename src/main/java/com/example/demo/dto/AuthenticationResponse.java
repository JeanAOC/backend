package com.example.demo.dto;

public class AuthenticationResponse {
    private String token;

    // Constructor
    public AuthenticationResponse(String token) {
        this.token = token;
    }

    // Getter y Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}