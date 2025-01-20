package org.example;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:gastos_ingresos.db";
    private Connection connection;


    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
    }


    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (dni TEXT PRIMARY KEY)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }


    public boolean authenticateUser(String dni) throws SQLException {
        String sql = "SELECT dni FROM usuarios WHERE dni = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }


    public boolean registerUser(String dni) throws SQLException {

        if (authenticateUser(dni)) {
            System.out.println("El usuario con DNI " + dni + " ya está registrado.");
            return false;
        }


        String sql = "INSERT INTO usuarios (dni) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            pstmt.executeUpdate();
            System.out.println("Usuario registrado exitosamente.");
            return true;
        }


    }



}
