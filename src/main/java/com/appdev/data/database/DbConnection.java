package com.appdev.data.database;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
  private static final Dotenv dotenv = Dotenv.load();
  private static final String DB_URL = dotenv.get("DB_URL");
  private static final String USER = dotenv.get("DB_USER");
  private static final String PASSWORD = dotenv.get("DB_PASSWORD");
  private static Connection connection;

  static {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    } catch (ClassNotFoundException | SQLException ex) {
      throw new RuntimeException("Failed to establish database connection", ex);
    }
  }

  public static Connection getConnection() {
    return connection;
  }

  public static void closeConnection() {
    if (connection != null) {
      try {
        if (!connection.isClosed()) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.err.println("Error closing database connection: " + ex.getMessage());
      } finally {
        connection = null;
      }
    }
  }
}
