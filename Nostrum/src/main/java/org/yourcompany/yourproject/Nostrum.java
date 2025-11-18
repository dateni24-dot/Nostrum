package org.yourcompany.yourproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Nostrum {
     public static final String URL = "jdbc:sqlite:Nostrum.db";

     public void createUser(String username, String password) {
         String sql = "INSERT INTO Users(username, password) VALUES(?, ?)";

         try (Connection conn = DriverManager.getConnection(URL);
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setString(1, username);
             pstmt.setString(2, password);
             pstmt.executeUpdate();
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
     }
     public void readUsers() {
         String sql = "SELECT ID, username, password FROM Users";

         try (Connection conn = DriverManager.getConnection(URL);
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery(sql)) {

             while (rs.next()) {
                 System.out.println("ID: " + rs.getInt("id") +
                                    ", Username: " + rs.getString("username") +
                                    ", Password: " + rs.getString("password"));
             }
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
     }
     public void updateUser(int id, String username, String password) {
         String sql = "UPDATE Users SET username = ?, password = ? WHERE ID = ?";

         try (Connection conn = DriverManager.getConnection(URL);
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setString(1, username);
             pstmt.setString(2, password);
             pstmt.setInt(3, id);
             pstmt.executeUpdate();
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
     }
     public void deleteUser(int id) {
         String sql = "DELETE FROM Users WHERE ID = ?";
            try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
     }
}
