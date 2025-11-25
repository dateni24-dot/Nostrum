package org.yourcompany.yourproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register2")
public class controlador extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String usuario = request.getParameter("usuario");
    String pass = request.getParameter("pass");

    String url = "jdbc:mysql://localhost:3306/tu_bbdd?useSSL=false&serverTimezone=UTC";
    String user = "root";
    String pwd = "";

    response.setContentType("text/plain; charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      try (Connection conn = DriverManager.getConnection(url, user, pwd)) {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO usuarios (usuario, pass) VALUES (?, ?)");
        ps.setString(1, usuario);
        ps.setString(2, pass);
        ps.executeUpdate();
        out.print("OK");
      } catch (SQLException e) {
        out.print("Error: " + e.getMessage());
      }
    }
  }
}
