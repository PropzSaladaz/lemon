package com.tecnico.lemon.auth;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AuthMap<U, T extends Authenticatable> implements IAuthenticationQueue<U, T> {
    private final Map<U, T> map = new ConcurrentHashMap<>();
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    @Override
    public Map<U, T> getMap() {
        return map;
    }

    @Override
    public void add(U id, T user) {
        map.put(id, user);
    }

    @Override
    public void remove(U id) {
        map.remove(id);

    }

    @Override
    public T get(U id) {
        return map.get(id);
    }

    @Override
    public boolean waitForAuthentication(U id, int WaitingLimitSeconds) {
        lock.lock();
        try {
            while(!get(id).isAuthenticated()){
                // If it is waiting for 1 minute, expires
                if (!condition.await(WaitingLimitSeconds, TimeUnit.SECONDS))
                    return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return true;
    }

    @Override
    public void authenticateUser(U id) {
        lock.lock();
        try {
            get(id).authenticate();
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void cancelAuthentication(U id) {
        lock.lock();
        get(id).cancelAuthentication();
        lock.unlock();
    }
}
