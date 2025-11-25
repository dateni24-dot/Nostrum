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
import jakarta.servlet.http.HttpSession;

@WebServlet("/loginJdbc")
public class LoginController extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String usuario = request.getParameter("usuario");
    String contrasena = request.getParameter("contrasena");

    response.setContentType("text/plain; charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      
      // Validar datos
      if (usuario == null || usuario.trim().isEmpty() ||
          contrasena == null || contrasena.isEmpty()) {
        out.print("Error: Usuario o contraseña vacíos");
        return;
      }

      // Hashear contraseña para compararla
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
          // Buscar usuario y verificar contraseña en tabla Users
          PreparedStatement ps = conn.prepareStatement(
            "SELECT id, username FROM Users WHERE username = ? AND password = ?"
          );
          ps.setString(1, usuario);
          ps.setString(2, hashedPassword);
          
          ResultSet rs = ps.executeQuery();
          
          if (rs.next()) {
            // Login exitoso - crear sesión
            HttpSession session = request.getSession();
            session.setAttribute("userId", rs.getInt("id"));
            session.setAttribute("usuario", usuario);
            
            out.print("OK");
          } else {
            out.print("Error: Usuario o contraseña incorrectos");
          }
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
      return password; // fallback
    }
  }
}
