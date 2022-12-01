package com.tecnico.lemon.database;

import org.postgresql.Driver;

import java.sql.*;
import java.util.Objects;

public class DatabaseManagerImpl implements DatabaseManager {

  private static final String database_name = "postgres";
  private static final String hostname = "localhost";
  private static final String port = "5432";
  private static final String username = "sirsadmin";
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
  public void populate() {
    executeQuery(Queries.insertUser(0, "admin@gmail.com", "1234", "Employer"));
    executeQuery(Queries.insertUser(1, "cust@gmail.com","1234", "Customer"));
    executeQuery(Queries.insertVehicle(1, 200, "https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12448.046022212302!2d-9.3042988!3d38.7404982!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x92000ad2cef547fa!2sTaguspark!5e0!3m2!1spt-PT!2spt!4v1669853654934!5m2!1spt-PT!2spt", "Scooter"));
    executeQuery(Queries.insertVehicle(2, 15, "https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12448.687282539375!2d-9.138705!3d38.7368192!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x880c7c731a54423!2sInstituto%20Superior%20T%C3%A9cnico!5e0!3m2!1spt-PT!2spt!4v1669853788927!5m2!1spt-PT!2spt", "Bike"));
    executeQuery(Queries.insertVehicle(2, 15, "https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12444.431467602202!2d-9.1617956!3d38.7612299!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0xc4fe8035597d8f22!2sEst%C3%A1dio%20Jos%C3%A9%20Alvalade!5e0!3m2!1spt-PT!2spt!4v1669853828021!5m2!1spt-PT!2spt", "Bike"));

  }
  @Override
  public void buildSchema(){
    clean();
    System.out.println(Queries.CREATE_TABLE_VEHICLES);
    executeQuery(Queries.CREATE_TABLE_USERS);
    executeQuery(Queries.CREATE_TABLE_VEHICLES);
    populate();
  }

  @Override
  public ResultSet executeQuery(String query) {
    try (Statement stmt = conn.createStatement()) {
      System.out.println(query);
      return stmt.executeQuery(query);
    } catch (SQLException e) {
      if (!Objects.equals(e.getMessage(), "No results were returned by the query."))
        e.printStackTrace();
    }
    return null;
  }

  @Override
  public void clean() {
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
