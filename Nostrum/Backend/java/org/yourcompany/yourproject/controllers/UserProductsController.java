package org.yourcompany.yourproject.controllers;

import java.io.IOException;
import java.util.List;

import org.yourcompany.yourproject.dao.UserProductDAO;
import org.yourcompany.yourproject.models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart/*")
public class UserProductsController extends HttpServlet {

    private UserProductDAO userProductDAO;

    @Override
    public void init() {
        userProductDAO = new UserProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            // Ver carrito
            List<Product> cartProducts = userProductDAO.getCartProducts(userId);
            request.setAttribute("cartProducts", cartProducts);
            request.getRequestDispatcher("/cart.jsp").forward(request, response);

        } else if (path.equals("/history")) {
            // Historial
            List<Product> history = userProductDAO.getHistoryProducts(userId);
            request.setAttribute("historyProducts", history);
            request.getRequestDispatcher("/history.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String path = request.getPathInfo();

        switch (path) {
            case "/add":
                {
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    userProductDAO.addToCart(userId, productId);
                    break;
                }
            case "/remove":
                {
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    userProductDAO.removeFromCart(userId, productId);
                    break;
                }
            case "/clear":
                userProductDAO.removeAllFromCart(userId);
                break;
            case "/buy":
                userProductDAO.buyProducts(userId);
                break;
            default:
                break;
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
