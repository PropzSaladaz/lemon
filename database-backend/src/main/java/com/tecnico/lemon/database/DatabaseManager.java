package com.tecnico.lemon.database;

import com.tecnico.lemon.contract.VehicleServiceOuterClass;

import java.sql.ResultSet;
import java.util.List;

import static com.tecnico.lemon.contract.VehicleServiceOuterClass.*;

public interface DatabaseManager {
    void buildSchema();
    void populate();
    ResultSet executeQuery(String query);
    void clean();
    void close();
}
