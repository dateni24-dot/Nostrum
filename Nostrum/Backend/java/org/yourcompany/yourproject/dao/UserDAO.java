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
        String catalina = System.getProperty("catalina.base");
        String dbPath = (catalina != null) 
            ? "jdbc:sqlite:" + catalina + "/Nostrum.db"
            : "jdbc:sqlite:Nostrum.db";
        return DriverManager.getConnection(dbPath);
    }

    public User login(String username, String password) {
        String sql = "SELECT id, username FROM Users WHERE username = ? AND password = ?";
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(String username, String password) {
        // Verificar si el usuario ya existe
        if (userExists(username)) {
            return false;
        }
        
        String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
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
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
