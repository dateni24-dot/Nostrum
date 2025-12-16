package org.yourcompany.yourproject.dao;

public class daotest {
    public static void main(String[] args) {
        //System.out.println("=== Test borrar de la cesta ===");
        UserProductDAO userProductDAO = new UserProductDAO();
        userProductDAO.removeAllFromCart(10);
        //userProductDAO.addToCart(9, 7);
        //userProductDAO.addToCart(9, 8);
        //userProductDAO.addToCart(10, 10);
    }

}
