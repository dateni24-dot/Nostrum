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
        
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

        User user = userDAO.login(usuario, contrasena);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("usuario", user.getUsername());
            response.sendRedirect(request.getContextPath() + "/welcome.html");
        } else {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("Usuario o contraseña incorrectos");
        }
    }
}
