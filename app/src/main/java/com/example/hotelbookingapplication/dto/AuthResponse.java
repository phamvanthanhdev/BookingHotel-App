package com.example.hotelbookingapplication.dto;

public class AuthResponse {
    private String jwt;
    private String message;
    private String role;

    public AuthResponse() {
    }

    public AuthResponse(String jwt, String message, String role) {
        this.jwt = jwt;
        this.message = message;
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "jwt='" + jwt + '\'' +
                ", message='" + message + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
