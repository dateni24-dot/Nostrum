package org.yourcompany.yourproject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import java.io.IOException;

@WebServlet("/register")
public class login_register extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Nostrum nostrum = new Nostrum();
        String username = request.getParameter("usuario");
        String password = request.getParameter("contraseña");
        nostrum.createUser(username, password);
    }
}
