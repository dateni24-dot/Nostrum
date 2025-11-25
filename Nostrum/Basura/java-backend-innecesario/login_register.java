package org.yourcompany.yourproject;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registerForm")
public class login_register extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Nostrum nostrum = new Nostrum();
        String username = request.getParameter("usuario");
        String password = request.getParameter("contrasena");
        nostrum.createUser(username, password);
    }
}
