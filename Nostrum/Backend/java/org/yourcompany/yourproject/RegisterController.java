package org.yourcompany.yourproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String usuario = request.getParameter("usuario");
    String contrasena = request.getParameter("contrasena");

    response.setContentType("text/plain; charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      
      // Validar datos
      if (usuario == null || usuario.trim().isEmpty() ||
          contrasena == null || contrasena.length() < 6) {
        out.print("Error: Usuario vacío o contraseña muy corta (mín 6 caracteres)");
        return;
      }

      // Hashear contraseña
      String hashedPassword = hashPassword(contrasena);

      try {
        // Cargar driver SQLite
        Class.forName("org.sqlite.JDBC");
        
        // Obtener ruta relativa al servidor
        String dbPath = System.getProperty("catalina.base") != null 
          ? System.getProperty("catalina.base") + "/Nostrum.db"
          : "Nostrum.db";
        
        String DB_URL = "jdbc:sqlite:" + dbPath;
        
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
          // Verificar si el usuario ya existe
          PreparedStatement checkPs = conn.prepareStatement("SELECT id FROM Users WHERE username = ?");
          checkPs.setString(1, usuario);
          ResultSet rs = checkPs.executeQuery();
          
          if (rs.next()) {
            out.print("Error: Usuario ya registrado");
            return;
          }

          // Insertar nuevo usuario en tabla Users
          PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO Users (username, password) VALUES (?, ?)"
          );
          ps.setString(1, usuario);
          ps.setString(2, hashedPassword);
          
          ps.executeUpdate();
          out.print("OK");
        }
      } catch (ClassNotFoundException e) {
        out.print("Error: Driver SQLite no encontrado - " + e.getMessage());
      } catch (SQLException e) {
        out.print("Error BD: " + e.getMessage());
      }
    }
  }

  private String hashPassword(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] messageDigest = md.digest(password.getBytes());
      return Base64.getEncoder().encodeToString(messageDigest);
    } catch (Exception e) {
      return password; // fallback si falla el hash
    }
  }
}