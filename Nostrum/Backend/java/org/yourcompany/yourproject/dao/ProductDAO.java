package org.yourcompany.yourproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.yourcompany.yourproject.models.Product;

public class ProductDAO {
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

    public List<Product> getAllProducts() {
        List<Product> productos = new ArrayList<>();
        String sql = "SELECT * FROM Product";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setImage_url(rs.getString("image_url"));
                p.setPrice(rs.getDouble("price"));
                
                productos.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }
}
