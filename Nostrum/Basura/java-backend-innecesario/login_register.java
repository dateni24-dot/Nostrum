package org.yourcompany.yourproject;
<<<<<<< HEAD:Nostrum/Basura/java-backend-innecesario/login_register.java
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registerForm")
=======
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import java.io.IOException;

@WebServlet("/register")
>>>>>>> 840543f1961ba1e965d97b138e5337924366e23e:Nostrum/src/main/java/org/yourcompany/yourproject/login_register.java
public class login_register extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Nostrum nostrum = new Nostrum();
        String username = request.getParameter("usuario");
<<<<<<< HEAD:Nostrum/Basura/java-backend-innecesario/login_register.java
        String password = request.getParameter("contrasena");
=======
        String password = request.getParameter("contraseña");
>>>>>>> 840543f1961ba1e965d97b138e5337924366e23e:Nostrum/src/main/java/org/yourcompany/yourproject/login_register.java
        nostrum.createUser(username, password);
    }
}
