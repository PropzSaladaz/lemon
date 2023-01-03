package com.tecnico.lemon.database;

import com.tecnico.lemon.contract.VehicleTableServiceOuterClass;

import java.sql.ResultSet;
import java.util.List;

import static com.tecnico.lemon.contract.VehicleTableServiceOuterClass.*;

public interface DatabaseManager {
    void buildSchema();
    void populate();
    ResultSet executeQuery(String query);
    void clean();
    void drop();
    void close();
    void initDatabase();
    String encrypt(String plaintext);
    String getB64EncodedSharedKey();
}
