package com.tecnico.lemon.database;

import com.tecnico.lemon.*;

import org.postgresql.Driver;

import java.security.PublicKey;
import java.sql.*;
import java.util.Objects;
import java.util.logging.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.crypto.KeyGenerator;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.util.UUID;

public class DatabaseManagerImpl implements DatabaseManager {
  private static final String database_name = "sirsdb";
  private static final String hostname = "localhost";
  private static final String port = "5432";
  private static final String username = "sirsdb_manager";
  private static final String password = "1234";

  private static Connection conn;

  public DatabaseManagerImpl() {

    String db_url = String.format("jdbc:postgresql://%s:%s/%s", hostname, port, database_name);
    try {
      DriverManager.registerDriver(new Driver());
      conn = DriverManager.getConnection(db_url, username, password);
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  @Override
  public void initDatabase() {
    try {
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(256); // specify the key size
      Key key = keyGenerator.generateKey();
      byte[] keyBytes = key.getEncoded();
      FileOutputStream keyOut = new FileOutputStream("src/main/credentials/shared-key.bin");
      keyOut.write(keyBytes);
      keyOut.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    buildSchema();
  }

  @Override 
  public String getB64EncodedSharedKey() {
    try {
      FileInputStream keyIn = new FileInputStream("src/main/credentials/shared-key.bin");
      byte[] keyBytes = new byte[keyIn.available()];
      keyIn.read(keyBytes);
      keyIn.close();
      return Base64.getEncoder().encodeToString(keyBytes);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  @Override 
  public String encrypt(String plaintext) {
    try {
      SecretKey key = AESKeyReader.readSharedKey("src/main/credentials/shared-key.bin");
      String ciphertext = Crypto.encryptAES(plaintext, key);
      System.out.println("Encrypted text: " + ciphertext);
      return ciphertext;

    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void populate() {
    try {
      PublicKey pk = RSAKeyReader.readPublic("src/main/credentials/employer-mobile-public.der");
      executeQuery(Queries.insertUser(KeyConverter.keyToString(pk), encrypt("admin@gmail.com"), "Employer"));
      executeQuery(Queries.insertVehicle(1, 200, "https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12448.046022212302!2d-9.3042988!3d38.7404982!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x92000ad2cef547fa!2sTaguspark!5e0!3m2!1spt-PT!2spt!4v1669853654934!5m2!1spt-PT!2spt", "Scooter"));
      executeQuery(Queries.insertVehicle(2, 15, "https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12448.687282539375!2d-9.138705!3d38.7368192!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x880c7c731a54423!2sInstituto%20Superior%20T%C3%A9cnico!5e0!3m2!1spt-PT!2spt!4v1669853788927!5m2!1spt-PT!2spt", "Bike"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @Override
  public void buildSchema(){
    drop(); // TODO use only for debug
    executeQuery(Queries.CREATE_TABLE_USERS);
    executeQuery(Queries.CREATE_TABLE_VEHICLES);
    executeQuery(Queries.CREATE_TABLE_USER_RESERVATIONS);
    executeQuery(Queries.CREATE_TABLE_RESERVATIONS);
    populate(); // TODO use only for debug
  }

  @Override
  public ResultSet executeQuery(String query) {
    try {
      System.out.println(query);
      Statement stmt = conn.createStatement();
      return stmt.executeQuery(query);
    } catch (SQLException e) {
      if (!Objects.equals(e.getMessage(), "No results were returned by the query."))
        e.printStackTrace();
    }
    return null;
  }

  
  @Override
  public void newVehicleReservation(int vehicle_id, String user_id) {
    try {
      // Get vehicle object given id if it exists
      ResultSet res = executeQuery(Queries.lookupVehicle(vehicle_id));
      res.next();
      if (!res.getString(Tables.Vehicle.RESERVATION_ID).equals("NULL")){
        throw new Exception("Trying to reserve an already reserved vehicle with reservation_id: " + res.getString(Tables.Vehicle.RESERVATION_ID));
      }

      // Generate a new reservation_id 
      String reservation_id = UUID.randomUUID().toString();
      System.out.printf("New reservation_id:'%s' for vehicle_id:('%s') from user_id:('%s')%n", reservation_id, vehicle_id, user_id);

      // Add vehicle to reservations table
      executeQuery(Queries.insertReservation(
        reservation_id,
        vehicle_id, 
        res.getString(Tables.Vehicle.LOCALIZATION), 
        res.getInt(Tables.Vehicle.PRICE),
        res.getString(Tables.Vehicle.DESCRIPTION)
      ));

      // Update vehicles reserved status in vehicle table
      executeQuery(Queries.updateVehicleReservationId(reservation_id, vehicle_id));

      // Add reservation->user to user_reservations table
      executeQuery(Queries.insertUserReservation(encrypt(reservation_id), user_id));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void deleteVehicleReservation(int vehicle_id) {
    try {
      // Remove entry from reservations table
      ResultSet res = executeQuery(Queries.lookupVehicle(vehicle_id));
      res.next();
      String reservation_id = res.getString(Tables.Vehicle.RESERVATION_ID);
      if (reservation_id.equals("NULL")) {
        throw new Exception("Trying to unlock an already unlocked vehicle with reservation_id: " + reservation_id);
      }
  
      executeQuery(Queries.deleteReservation(reservation_id));
  
      // Remove entry from user-reservations table
      executeQuery(Queries.deleteUserReservation(encrypt(reservation_id)));
  
      // Update vehicles reserved status in vehicle table
      executeQuery(Queries.updateVehicleReservationId("NULL", vehicle_id));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void clean() {
    executeQuery(Queries.DELETE_ALL_USERS);
    executeQuery(Queries.DELETE_ALL_VEHICLES);
    executeQuery(Queries.DELETE_ALL_RESERVATIONS);
    executeQuery(Queries.DELETE_ALL_USER_RESERVATIONS);
  }

  @Override
  public void drop() {
    executeQuery(Queries.DROP_TABLE_USERS);
    executeQuery(Queries.DROP_TABLE_VEHICLES);
    executeQuery(Queries.DROP_TABLE_RESERVATIONS);
    executeQuery(Queries.DROP_TABLE_USER_RESERVATIONS);
  }

  @Override
  public void close() {
    try {
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
