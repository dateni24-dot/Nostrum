package org.yourcompany.yourproject.controllers;

import java.io.IOException;
import java.util.List;

import org.yourcompany.yourproject.dao.ProductDAO;
import org.yourcompany.yourproject.models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/frontpage")
public class FrontPageController extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Product> productos = productDAO.getAllProducts();

        request.setAttribute("productos", productos);

        request.getRequestDispatcher("/frontpage.jsp").forward(request, response);
    }
}
