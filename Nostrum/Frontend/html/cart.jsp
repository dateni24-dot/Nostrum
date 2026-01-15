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
      <div class="cart-items" id="cartItemsContainer">
        <!-- Los productos se cargarán dinámicamente desde localStorage -->
      </div>

      <div class="order-summary" id="orderSummary" style="display: none;">
        <h2 class="summary-title">Resumen del Pedido</h2>
        <div class="summary-row">
          <span>Subtotal</span>
          <span id="subtotal">€0.00</span>
        </div>
        <div class="summary-row">
          <span>Envío</span>
          <span id="shipping">€4.99</span>
        </div>
        <div class="summary-row">
          <span>IVA (21%)</span>
          <span id="tax">€0.00</span>
        </div>
        <div class="summary-row total">
          <span>Total</span>
          <span id="total">€0.00</span>
        </div>

        <button class="checkout-btn" onclick="finalizarCompra()">Finalizar Compra</button>
        <button class="clear-cart-btn" onclick="vaciarCarrito()">Vaciar Carrito</button>
      </div>
    </div>
  </div>

  <script>
    // Cargar carrito desde localStorage
    function loadCart() {
      const cart = JSON.parse(localStorage.getItem('cart') || '[]');
      const container = document.getElementById('cartItemsContainer');
      const orderSummary = document.getElementById('orderSummary');
      
      console.log('🛒 Cart loaded:', cart); // Debug
      
      if (cart.length === 0) {
        container.innerHTML = '<div class="empty-cart">' +
          '<div class="empty-icon">🛒</div>' +
          '<div class="empty-text">Tu carrito está vacío</div>' +
          '<div class="empty-subtext">¡Agrega productos increíbles para gamers!</div>' +
          '<a href="/Nostrum/html/home.jsp" class="continue-btn">Explorar Productos</a>' +
          '</div>';
        orderSummary.style.display = 'none';
        return;
      }
      
      let subtotal = 0;
      container.innerHTML = '';
      
      cart.forEach(function(item, index) {
        const itemTotal = item.price * item.quantity;
        subtotal += itemTotal;
        
        // Usar la imagen guardada o mostrar fallback
        const imageUrl = item.image || '';
        
        console.log('Product:', item.name, 'Image URL:', imageUrl); // Debug
        
        let cartItemHTML = '<div class="cart-item">';
        
        if (imageUrl) {
          cartItemHTML += '<img src="' + imageUrl + '" alt="' + item.name + '" class="item-image" ' +
                         'onerror="this.style.display=\'none\'; this.nextElementSibling.style.display=\'flex\';" ' +
                         'style="width: 120px; height: 120px; object-fit: cover; border-radius: 10px;">' +
                         '<div class="fallback-icon" style="display: none; width: 120px; height: 120px; background: rgba(255,255,255,0.05); border-radius: 10px; align-items: center; justify-content: center; font-size: 3rem; flex-shrink: 0;">🎮</div>';
        } else {
          cartItemHTML += '<div class="fallback-icon" style="width: 120px; height: 120px; background: rgba(255,255,255,0.05); border-radius: 10px; display: flex; align-items: center; justify-content: center; font-size: 3rem; flex-shrink: 0;">🎮</div>';
        }
        
        cartItemHTML += '<div class="item-details" style="flex: 1; margin-left: 20px;">' +
          '<div class="item-name" style="font-size: 1.2rem; font-weight: 600; margin-bottom: 8px;">' + item.name + '</div>' +
          '<div class="item-description" style="color: #999; margin-bottom: 5px;">Precio unitario: €' + item.price.toFixed(2) + '</div>' +
          '<div style="display: flex; align-items: center; gap: 10px; margin-top: 10px;">' +
            '<button onclick="updateQuantity(' + index + ', -1)" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border: none; color: white; width: 32px; height: 32px; border-radius: 8px; cursor: pointer; font-size: 20px; display: flex; align-items: center; justify-content: center; transition: all 0.3s;">−</button>' +
            '<span style="font-size: 1.1rem; font-weight: 600; min-width: 40px; text-align: center; background: rgba(255,255,255,0.05); padding: 5px 15px; border-radius: 8px;">×' + item.quantity + '</span>' +
            '<button onclick="updateQuantity(' + index + ', 1)" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border: none; color: white; width: 32px; height: 32px; border-radius: 8px; cursor: pointer; font-size: 20px; display: flex; align-items: center; justify-content: center; transition: all 0.3s;">+</button>' +
          '</div>' +
        '</div>' +
        '<div class="item-actions" style="text-align: right; margin-left: 20px;">' +
          '<div class="price" style="font-size: 1.5rem; font-weight: 700; color: #667eea; margin-bottom: 15px;">€' + itemTotal.toFixed(2) + '</div>' +
          '<button class="remove-btn" onclick="removeFromCart(' + index + ')">Eliminar</button>' +
        '</div>' +
        '</div>';
        
        container.innerHTML += cartItemHTML;
      });
      
      // Calcular totales
      const shipping = subtotal > 50 ? 0 : 4.99;
      const tax = subtotal * 0.21;
      const total = subtotal + shipping + tax;
      
      document.getElementById('subtotal').textContent = '€' + subtotal.toFixed(2);
      document.getElementById('shipping').textContent = '€' + shipping.toFixed(2);
      document.getElementById('tax').textContent = '€' + tax.toFixed(2);
      document.getElementById('total').textContent = '€' + total.toFixed(2);
      
      orderSummary.style.display = 'block';
    }
    
    function updateQuantity(index, change) {
      let cart = JSON.parse(localStorage.getItem('cart') || '[]');
      cart[index].quantity += change;
      
      if (cart[index].quantity <= 0) {
        cart.splice(index, 1);
      }
      
      localStorage.setItem('cart', JSON.stringify(cart));
      loadCart();
    }
    
    function removeFromCart(index) {
      let cart = JSON.parse(localStorage.getItem('cart') || '[]');
      cart.splice(index, 1);
      localStorage.setItem('cart', JSON.stringify(cart));
      loadCart();
    }
    
    function vaciarCarrito() {
      if (confirm('¿Estás seguro de que quieres vaciar el carrito?')) {
        localStorage.removeItem('cart');
        loadCart();
      }
    }
    
    function finalizarCompra() {
      alert('🎉 ¡Gracias por tu compra!\n\nProcesamiento de pago en desarrollo...');
      localStorage.removeItem('cart');
      window.location.href = '/Nostrum/html/home.jsp';
    }
    
    // Cargar carrito al iniciar
    window.addEventListener('DOMContentLoaded', loadCart);
  </script>
</body>
</html>
