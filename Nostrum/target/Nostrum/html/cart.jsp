<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, org.yourcompany.yourproject.models.Product" %>
<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Carrito de Compras | Nostrum</title>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;800&display=swap" rel="stylesheet">
  <link href="/Nostrum/css/styles.css" rel="stylesheet">
  <style>
    body {
      background: linear-gradient(135deg, #0f0c29 0%, #302b63 50%, #24243e 100%);
      min-height: 100vh;
      color: #fff;
      font-family: 'Inter', sans-serif;
    }

    .cart-container {
      max-width: 1200px;
      margin: 40px auto;
      padding: 0 20px;
    }

    .cart-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 30px;
    }

    .cart-title {
      font-size: 2.5rem;
      font-weight: 800;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }

    .back-btn {
      display: flex;
      align-items: center;
      gap: 8px;
      padding: 12px 24px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border: none;
      border-radius: 10px;
      color: white;
      font-weight: 600;
      cursor: pointer;
      text-decoration: none;
      transition: all 0.3s ease;
    }

    .back-btn:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
    }

    .cart-content {
      display: grid;
      grid-template-columns: 1fr 400px;
      gap: 30px;
    }

    .cart-items {
      display: flex;
      flex-direction: column;
      gap: 20px;
    }

    .cart-item {
      background: linear-gradient(145deg, #1a1a2e 0%, #16213e 100%);
      border-radius: 15px;
      padding: 20px;
      display: grid;
      grid-template-columns: 120px 1fr auto;
      gap: 20px;
      align-items: center;
      box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
      transition: all 0.3s ease;
    }

    .cart-item:hover {
      transform: translateY(-2px);
      box-shadow: 0 12px 40px rgba(102, 126, 234, 0.2);
    }

    .item-image {
      width: 120px;
      height: 120px;
      object-fit: cover;
      border-radius: 10px;
      background: rgba(255, 255, 255, 0.05);
    }

    .item-details {
      flex: 1;
    }

    .item-name {
      font-size: 1.3rem;
      font-weight: 700;
      margin-bottom: 8px;
      color: #fff;
    }

    .item-description {
      color: rgba(255, 255, 255, 0.6);
      font-size: 0.95rem;
      line-height: 1.5;
    }

    .item-actions {
      display: flex;
      flex-direction: column;
      align-items: flex-end;
      gap: 15px;
    }

    .price {
      font-size: 1.5rem;
      font-weight: 800;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }

    .remove-btn {
      padding: 8px 12px;
      background: rgba(255, 59, 48, 0.1);
      border: 1px solid rgba(255, 59, 48, 0.3);
      border-radius: 8px;
      color: #ff3b30;
      cursor: pointer;
      transition: all 0.3s ease;
      font-size: 0.9rem;
      border: none;
    }

    .remove-btn:hover {
      background: rgba(255, 59, 48, 0.2);
      border-color: rgba(255, 59, 48, 0.5);
    }

    .empty-cart {
      text-align: center;
      padding: 60px 20px;
      background: linear-gradient(145deg, #1a1a2e 0%, #16213e 100%);
      border-radius: 15px;
      box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
      grid-column: 1 / -1;
    }

    .empty-icon {
      font-size: 4rem;
      margin-bottom: 20px;
    }

    .empty-text {
      font-size: 1.5rem;
      font-weight: 600;
      margin-bottom: 10px;
    }

    .empty-subtext {
      color: rgba(255, 255, 255, 0.6);
      margin-bottom: 30px;
    }

    .continue-btn {
      display: inline-block;
      padding: 12px 30px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border-radius: 10px;
      color: white;
      text-decoration: none;
      font-weight: 600;
      transition: all 0.3s ease;
    }

    .continue-btn:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
    }

    .order-summary {
      background: linear-gradient(145deg, #1a1a2e 0%, #16213e 100%);
      border-radius: 15px;
      padding: 25px;
      height: fit-content;
      box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
      position: sticky;
      top: 20px;
    }

    .summary-title {
      font-size: 1.5rem;
      font-weight: 700;
      margin-bottom: 20px;
      color: #fff;
    }

    .summary-row {
      display: flex;
      justify-content: space-between;
      margin-bottom: 15px;
      font-size: 1rem;
    }

    .summary-row.total {
      font-size: 1.3rem;
      font-weight: 700;
      padding-top: 15px;
      border-top: 2px solid rgba(255, 255, 255, 0.1);
      margin-top: 15px;
    }

    .checkout-btn {
      width: 100%;
      padding: 15px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border: none;
      border-radius: 10px;
      color: white;
      font-size: 1.1rem;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.3s ease;
      margin-top: 20px;
    }

    .checkout-btn:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
    }

    .clear-cart-btn {
      width: 100%;
      padding: 12px;
      background: rgba(255, 59, 48, 0.1);
      border: 1px solid rgba(255, 59, 48, 0.3);
      border-radius: 10px;
      color: #ff3b30;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.3s ease;
      margin-top: 10px;
    }

    .clear-cart-btn:hover {
      background: rgba(255, 59, 48, 0.2);
    }

    @media (max-width: 768px) {
      .cart-content {
        grid-template-columns: 1fr;
      }

      .cart-item {
        grid-template-columns: 80px 1fr;
        gap: 15px;
      }

      .item-image {
        width: 80px;
        height: 80px;
      }

      .item-actions {
        grid-column: 1 / -1;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
      }

      .order-summary {
        position: static;
      }
    }
  </style>
</head>
<body>
  <div class="cart-container">
    <div class="cart-header">
      <h1 class="cart-title">Mi Carrito</h1>
      <a href="/Nostrum/html/home.jsp" class="back-btn">
        ← Seguir Comprando
      </a>
    </div>

    <div class="cart-content">
      <div class="cart-items">
        <%
          List<Product> cartProducts = (List<Product>) request.getAttribute("cartProducts");
          if (cartProducts == null || cartProducts.isEmpty()) {
        %>
          <div class="empty-cart">
            <div class="empty-icon">🛒</div>
            <div class="empty-text">Tu carrito está vacío</div>
            <div class="empty-subtext">¡Agrega productos increíbles para gamers!</div>
            <a href="/Nostrum/html/home.jsp" class="continue-btn">Explorar Productos</a>
          </div>
        <%
          } else {
            double subtotal = 0;
            for (Product product : cartProducts) {
              subtotal += product.getPrice();
        %>
          <div class="cart-item">
            <img src="<%= product.getImage_url() %>" alt="<%= product.getName() %>" class="item-image">
            <div class="item-details">
              <div class="item-name"><%= product.getName() %></div>
              <div class="item-description"><%= product.getDescription() %></div>
            </div>
            <div class="item-actions">
              <div class="price">€<%= String.format("%.2f", product.getPrice()) %></div>
              <form action="/Nostrum/cart/remove" method="post" style="display: inline;">
                <input type="hidden" name="productId" value="<%= product.getId() %>">
                <button type="submit" class="remove-btn">Eliminar</button>
              </form>
            </div>
          </div>
        <%
            }
        %>
      </div>

      <div class="order-summary">
        <h2 class="summary-title">Resumen del Pedido</h2>
        <div class="summary-row">
          <span>Subtotal</span>
          <span>€<%= String.format("%.2f", subtotal) %></span>
        </div>
        <div class="summary-row">
          <span>Envío</span>
          <span>€<%= String.format("%.2f", subtotal > 50 ? 0.0 : 4.99) %></span>
        </div>
        <div class="summary-row">
          <span>IVA (21%)</span>
          <span>€<%= String.format("%.2f", subtotal * 0.21) %></span>
        </div>
        <div class="summary-row total">
          <span>Total</span>
          <span>€<%= String.format("%.2f", subtotal + (subtotal > 50 ? 0 : 4.99) + (subtotal * 0.21)) %></span>
        </div>

        <form action="/Nostrum/cart/buy" method="post">
          <button type="submit" class="checkout-btn">Finalizar Compra</button>
        </form>
        
        <form action="/Nostrum/cart/clear" method="post">
          <button type="submit" class="clear-cart-btn">Vaciar Carrito</button>
        </form>
      </div>
      <%
          }
      %>
    </div>
  </div>
</body>
</html>
