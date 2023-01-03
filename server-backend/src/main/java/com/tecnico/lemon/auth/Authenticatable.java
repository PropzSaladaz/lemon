package com.tecnico.lemon.auth;

public interface Authenticatable {
    boolean isAuthenticated();
    void authenticate();
    void cancelAuthentication();
}
