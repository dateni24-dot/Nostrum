package org.yourcompany.yourproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.yourcompany.yourproject.models.Product;

public class UserProductDAO {
    
    private Connection getConnection() throws SQLException {
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

    public List<Product> getProductsByUserId(int userId) {
        List<Product> productos = new ArrayList<>();
        String sql = "SELECT p.* FROM Product p "
                   + "JOIN user_products up ON p.id = up.product_id "
                   + "WHERE up.user_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("description"));
                    p.setImage_url(rs.getString("image_url"));
                    p.setPrice(rs.getDouble("price"));
                    
                    productos.add(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return productos;
    }
    
    public void addToCart(int userId, int productId) {
        String sql = "INSERT INTO user_products (user_id, product_id) VALUES (?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void removeFromCart(int userId, int productId) {
        String sql = "DELETE FROM user_products WHERE user_id = ? AND product_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void removeAllFromCart(int userId) {
        String sql = "DELETE FROM user_products WHERE user_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void buyProducts(int userId) {
        String sql = "UPDATE user_products SET purchased = 1 WHERE user_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        

}   }
public void getHistoryProducts(int userId) {
        String sql = "SELECT p.* FROM user_products where purchased = 1 JOIN Product p ON p.id = up.product_id WHERE up.user_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("description"));
                    p.setImage_url(rs.getString("image_url"));
                    p.setPrice(rs.getDouble("price"));
                    
                    System.out.println(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
}   
    }
}       
