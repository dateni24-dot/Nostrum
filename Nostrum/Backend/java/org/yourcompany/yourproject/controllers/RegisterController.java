package org.yourcompany.yourproject.controllers;

import java.io.IOException;

import org.yourcompany.yourproject.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("========================================");
        System.out.println("🚀 RegisterController.doPost INVOCADO");
        System.out.println("========================================");
        
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");
        
        System.out.println("📥 Parámetros recibidos:");
        System.out.println("   Usuario: " + usuario);
        System.out.println("   Contraseña: " + (contrasena != null ? "[" + contrasena.length() + " caracteres]" : "null"));

        response.setContentType("text/plain; charset=UTF-8");

        // Validaciones
        if (usuario == null || usuario.trim().isEmpty() ||
            contrasena == null || contrasena.length() < 6) {
            response.getWriter().write("Datos inválidos");
            return;
        }

        if (userDAO.register(usuario, contrasena)) {
            response.getWriter().write("OK");
        } else {
            response.getWriter().write("El usuario ya existe");
        }
    }
}
