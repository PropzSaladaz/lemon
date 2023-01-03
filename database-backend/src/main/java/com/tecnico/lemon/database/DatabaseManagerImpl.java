package com.tecnico.lemon.database;

import com.tecnico.lemon.KeyGenerate;
import com.tecnico.lemon.AESKeyReader;

import org.postgresql.Driver;

import java.sql.*;
import java.util.Objects;
import java.util.logging.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.crypto.KeyGenerator;
import com.tecnico.lemon.KeyGenerate;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
    buildSchema();
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
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, key);
      String ciphertext = Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8)));
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
    executeQuery(Queries.insertUser("lala", "admin@gmail.com", "Employer"));
    executeQuery(Queries.insertUser("lolo","cust@gmail.com", "Customer"));
    executeQuery(Queries.insertVehicle(1, 200, "https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12448.046022212302!2d-9.3042988!3d38.7404982!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x92000ad2cef547fa!2sTaguspark!5e0!3m2!1spt-PT!2spt!4v1669853654934!5m2!1spt-PT!2spt", "Scooter"));
    executeQuery(Queries.insertVehicle(2, 15, "https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12448.687282539375!2d-9.138705!3d38.7368192!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x880c7c731a54423!2sInstituto%20Superior%20T%C3%A9cnico!5e0!3m2!1spt-PT!2spt!4v1669853788927!5m2!1spt-PT!2spt", "Bike"));

  }
  @Override
  public void buildSchema(){
    drop(); // TODO use only for debug
    executeQuery(Queries.CREATE_TABLE_USERS);
    executeQuery(Queries.CREATE_TABLE_VEHICLES);
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
  public void clean() {
    executeQuery(Queries.DELETE_ALL_USERS);
    executeQuery(Queries.DELETE_ALL_VEHICLES);
  }

  @Override
  public void drop() {
    executeQuery(Queries.DROP_TABLE_USERS);
    executeQuery(Queries.DROP_TABLE_VEHICLES);
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
