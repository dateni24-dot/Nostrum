package org.yourcompany.yourproject.controllers;

import java.io.IOException;

import org.yourcompany.yourproject.dao.UserDAO;
import org.yourcompany.yourproject.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/loginJdbc")
public class LoginController extends HttpServlet {
    
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("========================================");
        System.out.println("🔐 LoginController.doPost INVOCADO");
        System.out.println("========================================");
        
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");
        
        System.out.println("📥 Intentando login:");
        System.out.println("   Usuario: " + usuario);
        System.out.println("   Contraseña: " + (contrasena != null ? "[" + contrasena.length() + " caracteres]" : "null"));

        User user = userDAO.login(usuario, contrasena);

        response.setContentType("text/plain; charset=UTF-8");

        if (user != null) {
            System.out.println("✓ Login exitoso para: " + usuario);
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("usuario", user.getUsername());
            response.getWriter().write("OK");
        } else {
            System.out.println("❌ Login fallido para: " + usuario);
            response.getWriter().write("Usuario o contraseña incorrectos");
        }
    }
}
