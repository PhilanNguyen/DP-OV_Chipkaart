package nl.hu.ipass.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/ovchip";
        String username = "postgres";
        String password = "ikhouvansql";
        return DriverManager.getConnection(url, username, password);
    }
}
