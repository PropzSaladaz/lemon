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
    String decrypt(String ciphertext);
    String getB64EncodedSharedKey();
    void newVehicleReservation(int vehicle_id, String userId);
    void deleteVehicleReservation(int vehicle_id);
    List<Integer> getUserReservedVehicles(String user_id);
}
