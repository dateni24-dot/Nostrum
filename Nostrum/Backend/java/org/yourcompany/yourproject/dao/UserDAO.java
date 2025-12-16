package org.yourcompany.yourproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.yourcompany.yourproject.models.User;
import org.yourcompany.yourproject.util.PasswordUtil;

public class UserDAO {
    
    private Connection getConnection() throws SQLException {
        // Cargar el driver explícitamente
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver SQLite no encontrado", e);
        }
        
        String catalina = System.getProperty("catalina.base");
        String dbPath = (catalina != null) 
            ? "jdbc:sqlite:" + catalina + "/Nostrum.db"
            : "jdbc:sqlite:Nostrum.db";
        return DriverManager.getConnection(dbPath);
    }

    public User login(String username, String password) {
        String sql = "SELECT id, username FROM Users WHERE username = ? AND password = ?";
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        System.out.println(" Intentando login - Usuario: " + username);
        System.out.println(" Password hasheado: " + hashedPassword);
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("✓ Login exitoso: " + username);
                    return new User(rs.getInt("id"), rs.getString("username"));
                } else {
                    System.out.println("❌ Login fallido: usuario/contraseña no coinciden");
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error SQL en login: " + username);
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(String username, String password) {
        // Verificar si el usuario ya existe
        if (userExists(username)) {
            System.out.println("❌ Usuario ya existe: " + username);
            return false;
        }
        
        String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            int rows = stmt.executeUpdate();
            System.out.println("✓ Usuario registrado: " + username + " (rows: " + rows + ")");
            return true;
            
        } catch (SQLException e) {
            System.err.println("❌ Error SQL al registrar usuario: " + username);
            e.printStackTrace();
            return false;
        }
    }

    private boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("🔍 Verificando usuario '" + username + "': count=" + count);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error SQL al verificar usuario: " + username);
            e.printStackTrace();
        }
        return false;
    }
}
