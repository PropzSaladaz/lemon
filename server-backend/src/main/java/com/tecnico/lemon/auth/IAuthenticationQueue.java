package com.tecnico.lemon.auth;

import java.util.Map;

public interface IAuthenticationQueue<U, T> {

    Map<U, T> getMap();
    void add(U id, T user);
    void remove(U id);
    T get(U id);
    boolean waitForAuthentication(U id, int WaitingLimitSeconds);
    void authenticateUser(U id);
    void cancelAuthentication(U id);
}
