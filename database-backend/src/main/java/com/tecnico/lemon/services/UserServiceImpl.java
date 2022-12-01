package com.tecnico.lemon.services;

import com.tecnico.lemon.contract.UserServiceGrpc;
import com.tecnico.lemon.database.DatabaseManager;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    DatabaseManager _db;

    public UserServiceImpl(DatabaseManager db) {
        _db = db;
    }
}
