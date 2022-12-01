package com.tecnico.lemon.database;

import org.postgresql.Driver;

import java.sql.*;

public class DatabaseManagerImpl implements DatabaseManager {

  private static final String database = "postgres";
  private static final String hostname = "localhost";
  private static final String username = "sirsdb_manager";
  private static final String password = "1234";
  private static final String port = "5432";

  private static Connection conn;

  public DatabaseManagerImpl() {

    String url = String.format("jdbc:postgresql://%s:%s/%s", hostname, port, database);
    try {
      DriverManager.registerDriver(new Driver());
      conn = DriverManager.getConnection(url, username, password);
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  @Override
  public ResultSet executeQuery(String query) {
    try (Statement stmt = conn.createStatement()) {
      return stmt.executeQuery(query);
    } catch (SQLException e) {
      e.printStackTrace();
      System.exit(0);
    }
    return null;
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
