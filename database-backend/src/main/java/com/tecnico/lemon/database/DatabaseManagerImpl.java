package com.tecnico.lemon.database;

import org.postgresql.Driver;

import java.sql.*;
import java.util.Objects;
import java.util.logging.Logger;

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
  public void populate() {
    executeQuery(Queries.insertUser(0, "admin@gmail.com", "1234", "Employer"));
    executeQuery(Queries.insertUser(1, "cust@gmail.com", "1234", "Customer"));
    executeQuery(Queries.insertVehicle(1, 200, "baba", "Scooter"));
    executeQuery(Queries.insertVehicle(2, 15, "bobo", "Bike"));
  }
  @Override
  public void buildSchema(){
    drop();
    executeQuery(Queries.CREATE_TABLE_USERS);
    executeQuery(Queries.CREATE_TABLE_VEHICLES);
//    populate();
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
