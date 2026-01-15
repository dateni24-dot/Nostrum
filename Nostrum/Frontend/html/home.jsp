<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,org.yourcompany.yourproject.models.Product,org.yourcompany.yourproject.dao.ProductDAO" %>

<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Nostrum | Gaming Marketplace</title>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;800&display=swap" rel="stylesheet">
  <link href="/Nostrum/css/styles.css" rel="stylesheet">
  <link href="/Nostrum/css/home.css" rel="stylesheet">
</head>
<body class="home-body">
  <!-- Header -->
  <header class="main-header">
    <div class="logo-header">Nostrum</div>
    <div class="header-right">
      <div class="user-info-header">
        <span id="username">Usuario</span>
      </div>
      <a href="/Nostrum/html/cart.jsp" class="icon-btn" title="Carrito">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="9" cy="21" r="1"/><circle cx="20" cy="21" r="1"/>
          <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
        </svg>
        <span class="cart-badge">0</span>
      </a>
      <a href="/Nostrum/profile.html" class="icon-btn" title="Perfil">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
          <circle cx="12" cy="7" r="4"/>
        </svg>
      </a>
    </div>
  </header>

  <!-- Search Bar -->
  <div class="search-container">
    <div class="search-bar">
      <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
        <circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/>
      </svg>
      <input type="text" placeholder="Buscar productos gaming..." id="searchInput">
      <button class="search-btn">Buscar</button>
    </div>
  </div>

  <!-- Main Content -->
  <main class="main-content">
    <!-- Search Results Container -->
    <div id="searchResults" style="display: none; max-width: 1400px; margin: 0 auto 2rem; padding: 0 20px;">
      <div style="background: linear-gradient(145deg, #1a1a2e 0%, #16213e 100%); border-radius: 15px; padding: 20px;">
        <h3 style="margin: 0 0 15px 0; color: #667eea;">Resultados de búsqueda</h3>
        <div id="searchResultsGrid" style="display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 1.5rem;"></div>
      </div>
    </div>

    <!-- Products Carousel -->
    <%
        // Cargar productos para el carrusel (primeros 6 productos aleatorios)
        ProductDAO productDAOCarousel = new ProductDAO();
        List<Product> productosCarousel = productDAOCarousel.getAllProducts();
        java.util.Collections.shuffle(productosCarousel);
        if (productosCarousel.size() > 6) {
            productosCarousel = productosCarousel.subList(0, 6);
        }
    %>
    <section class="popular-section">
      <div class="section-header">
        <h2>🔥 Productos Populares</h2>
        <div class="carousel-controls">
          <button class="carousel-btn prev" onclick="scrollCarousel(-1)">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M15 18l-6-6 6-6"/>
            </svg>
          </button>
          <button class="carousel-btn next" onclick="scrollCarousel(1)">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M9 18l6-6-6-6"/>
            </svg>
          </button>
        </div>
      </div>
      
      <div class="carousel-wrapper">
        <div class="carousel" id="popularCarousel">
          <% 
          String[] badges = {"HOT", "NEW", "", "HOT", "NEW", ""};
          int badgeIndex = 0;
          for (Product producto : productosCarousel) { 
            String badge = badges[badgeIndex++ % badges.length];
          %>
          <div class="product-card">
            <div class="product-image">
              <img src="<%= producto.getImage_url() %>" alt="<%= producto.getName() %>" onerror="this.src='https://via.placeholder.com/400x400/1a1a2e/667eea?text=Sin+Imagen'">
              <% if (!badge.isEmpty()) { %>
                <span class="badge <%= badge.toLowerCase() %>"><%= badge %></span>
              <% } %>
            </div>
            <div class="product-info">
              <h3><%= producto.getName() %></h3>
              <p class="product-desc"><%= producto.getDescription() %></p>
              <div class="product-footer">
                <span class="price">€<%= String.format("%.2f", producto.getPrice()) %></span>
                <button class="add-cart-btn" data-id="<%= producto.getId() %>" data-name="<%= producto.getName() %>" data-price="<%= producto.getPrice() %>">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M12 5v14M5 12h14"/>
                  </svg>
                </button>
              </div>
            </div>
          </div>
          <% } %>
        </div>
      </div>
    </section>

    <!-- Products by Category Section -->
    <%
        // Cargar productos desde la base de datos
        List<Product> productos = (List<Product>) request.getAttribute("productos");
        if (productos == null) {
            ProductDAO productDAO = new ProductDAO();
            productos = productDAO.getAllProducts();
        }
        
        // Organizar productos por categoría basado en el nombre
        java.util.Map<String, java.util.List<Product>> productosPorCategoria = new java.util.LinkedHashMap<>();
        if (productos != null && !productos.isEmpty()) {
            for (Product p : productos) {
                String categoria = "Otros";
                String nombre = p.getName().toLowerCase();
                
                // Clasificar por palabras clave
                if (nombre.contains("ratón") || nombre.contains("raton") || nombre.contains("mouse")) {
                    categoria = "Ratones Gaming";
                } else if (nombre.contains("teclado") || nombre.contains("keyboard")) {
                    categoria = "Teclados Mecánicos";
                } else if (nombre.contains("auricular") || nombre.contains("headset") || nombre.contains("casco")) {
                    categoria = "Auriculares";
                } else if (nombre.contains("silla") || nombre.contains("chair")) {
                    categoria = "Sillas Gaming";
                } else if (nombre.contains("rtx") || nombre.contains("gráfica") || nombre.contains("gpu")) {
                    categoria = "Componentes PC";
                } else if (nombre.contains("ssd") || nombre.contains("disco")) {
                    categoria = "Almacenamiento";
                } else if (nombre.contains("funko") || nombre.contains("figura")) {
                    categoria = "Coleccionables";
                } else if (nombre.contains("switch") || nombre.contains("consola")) {
                    categoria = "Consolas";
                }
                
                if (!productosPorCategoria.containsKey(categoria)) {
                    productosPorCategoria.put(categoria, new java.util.ArrayList<>());
                }
                productosPorCategoria.get(categoria).add(p);
            }
        }
        
        // Mostrar productos por categoría
        for (java.util.Map.Entry<String, java.util.List<Product>> entry : productosPorCategoria.entrySet()) {
            String categoria = entry.getKey();
            java.util.List<Product> productosCategoria = entry.getValue();
    %>
    
    <section class="popular-section">
      <div class="section-header">
        <h2>🎮 <%= categoria %></h2>
      </div>
      
      <div class="products-grid" style="display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 1.5rem; max-width: 1400px; margin: 0 auto 3rem; padding: 0 20px;">
        <% for (Product producto : productosCategoria) { %>
          <div class="product-card">
            <div class="product-image">
              <img src="<%= producto.getImage_url() %>" alt="<%= producto.getName() %>" onerror="this.src='https://via.placeholder.com/400x400/1a1a2e/667eea?text=Sin+Imagen'">
            </div>
            <div class="product-info">
              <h3><%= producto.getName() %></h3>
              <p class="product-desc"><%= producto.getDescription() %></p>
              <div class="product-footer">
                <span class="price">€<%= String.format("%.2f", producto.getPrice()) %></span>
                <button class="add-cart-btn" data-id="<%= producto.getId() %>" data-name="<%= producto.getName() %>" data-price="<%= producto.getPrice() %>">
                  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="9" cy="21" r="1"/><circle cx="20" cy="21" r="1"/>
                    <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
                  </svg>
                </button>
              </div>
            </div>
          </div>
        <% } %>
      </div>
    </section>
    
    <% } %>

    <!-- News and Categories Grid -->
    <div class="content-grid">
      <!-- News Panel -->
      <aside class="news-panel">
        <h2>Noticias y Novedades</h2>
        <div class="news-list">
          <article class="news-item">
            <div class="news-date">27 Nov 2025</div>
            <h3>Nuevo DLC de Elden Ring</h3>
            <p>Shadow of the Erdtree ya disponible con nuevos jefes épicos...</p>
            <a href="#" class="read-more">Leer más →</a>
          </article>
          
          <article class="news-item">
            <div class="news-date">26 Nov 2025</div>
            <h3>Black Friday Gaming</h3>
            <p>Ofertas increíbles hasta el 30% en periféricos seleccionados...</p>
            <a href="#" class="read-more">Leer más →</a>
          </article>
          
          <article class="news-item">
            <div class="news-date">25 Nov 2025</div>
            <h3>PS5 Pro en Stock</h3>
            <p>Nuevas unidades disponibles de la consola más potente...</p>
            <a href="#" class="read-more">Leer más →</a>
          </article>

          <article class="news-item">
            <div class="news-date">24 Nov 2025</div>
            <h3>Torneo Valorant</h3>
            <p>Inscripciones abiertas para el torneo de la comunidad...</p>
            <a href="#" class="read-more">Leer más →</a>
          </article>
        </div>
      </aside>

      <!-- Categories -->
      <section class="categories-section">
        <h2>🎮 Explora por Categorías</h2>
        
        <div class="categories-grid">
          <!-- Videojuegos -->
          <div class="category-card">
            <div class="category-icon">
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                <rect x="2" y="7" width="20" height="15" rx="2" ry="2"/>
                <polyline points="17 2 12 7 7 2"/>
              </svg>
            </div>
            <h3>Videojuegos</h3>
            <p>PS5, Xbox, Switch, PC</p>
            <span class="item-count">120+ productos</span>
          </div>

          <!-- Periféricos -->
          <div class="category-card">
            <div class="category-icon">
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                <rect x="2" y="4" width="20" height="16" rx="2"/>
                <path d="M6 8h.01M10 8h.01M6 12h.01M10 12h.01M6 16h.01M10 16h.01"/>
              </svg>
            </div>
            <h3>Periféricos</h3>
            <p>Teclados, Ratones, Auriculares</p>
            <span class="item-count">85+ productos</span>
          </div>

          <!-- Sillas Gaming -->
          <div class="category-card">
            <div class="category-icon">
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                <path d="M12 19v-7m0 0V5m0 7H5m7 0h7"/>
                <rect x="3" y="3" width="18" height="18" rx="2"/>
              </svg>
            </div>
            <h3>Sillas Gaming</h3>
            <p>Ergonómicas y Profesionales</p>
            <span class="item-count">45+ productos</span>
          </div>

          <!-- Figuras y Coleccionables -->
          <div class="category-card">
            <div class="category-icon">
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                <circle cx="12" cy="8" r="3"/>
                <path d="M12 11v10m-3-6l3-3 3 3M7 21h10"/>
              </svg>
            </div>
            <h3>Figuras</h3>
            <p>Coleccionables y Merchandising</p>
            <span class="item-count">150+ productos</span>
          </div>

          <!-- Componentes PC -->
          <div class="category-card">
            <div class="category-icon">
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                <rect x="4" y="4" width="16" height="16" rx="2"/>
                <rect x="9" y="9" width="6" height="6"/>
                <line x1="9" y1="1" x2="9" y2="4"/><line x1="15" y1="1" x2="15" y2="4"/>
                <line x1="9" y1="20" x2="9" y2="23"/><line x1="15" y1="20" x2="15" y2="23"/>
              </svg>
            </div>
            <h3>Componentes PC</h3>
            <p>GPUs, CPUs, RAM, Almacenamiento</p>
            <span class="item-count">200+ productos</span>
          </div>

          <!-- Monitores -->
          <div class="category-card">
            <div class="category-icon">
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                <rect x="2" y="3" width="20" height="14" rx="2"/>
                <line x1="8" y1="21" x2="16" y2="21"/>
                <line x1="12" y1="17" x2="12" y2="21"/>
              </svg>
            </div>
            <h3>Monitores</h3>
            <p>144Hz+, 4K, Ultrawide</p>
            <span class="item-count">60+ productos</span>
          </div>
        </div>
      </section>
    </div>
  </main>

  <!-- Footer -->
  <footer class="main-footer">
    <div class="footer-content">
      <div class="footer-section">
        <h3>Nostrum Gaming</h3>
        <p>Tu marketplace de confianza para todo lo gaming</p>
      </div>
      
      <div class="footer-section">
        <h4>Enlaces</h4>
        <ul>
          <li><a href="#">Sobre Nosotros</a></li>
          <li><a href="#">Términos y Condiciones</a></li>
          <li><a href="#">Política de Privacidad</a></li>
          <li><a href="#">Soporte</a></li>
        </ul>
      </div>

      <div class="footer-section">
        <h4>Síguenos</h4>
        <div class="social-links">
          <a href="#" title="Discord">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
              <path d="M20.317 4.37a19.791 19.791 0 0 0-4.885-1.515a.074.074 0 0 0-.079.037c-.21.375-.444.864-.608 1.25a18.27 18.27 0 0 0-5.487 0a12.64 12.64 0 0 0-.617-1.25a.077.077 0 0 0-.079-.037A19.736 19.736 0 0 0 3.677 4.37a.07.07 0 0 0-.032.027C.533 9.046-.32 13.58.099 18.057a.082.082 0 0 0 .031.057a19.9 19.9 0 0 0 5.993 3.03a.078.078 0 0 0 .084-.028a14.09 14.09 0 0 0 1.226-1.994a.076.076 0 0 0-.041-.106a13.107 13.107 0 0 1-1.872-.892a.077.077 0 0 1-.008-.128a10.2 10.2 0 0 0 .372-.292a.074.074 0 0 1 .077-.01c3.928 1.793 8.18 1.793 12.062 0a.074.074 0 0 1 .078.01c.12.098.246.198.373.292a.077.077 0 0 1-.006.127a12.299 12.299 0 0 1-1.873.892a.077.077 0 0 0-.041.107c.36.698.772 1.362 1.225 1.993a.076.076 0 0 0 .084.028a19.839 19.839 0 0 0 6.002-3.03a.077.077 0 0 0 .032-.054c.5-5.177-.838-9.674-3.549-13.66a.061.061 0 0 0-.031-.03zM8.02 15.33c-1.183 0-2.157-1.085-2.157-2.419c0-1.333.956-2.419 2.157-2.419c1.21 0 2.176 1.096 2.157 2.42c0 1.333-.956 2.418-2.157 2.418zm7.975 0c-1.183 0-2.157-1.085-2.157-2.419c0-1.333.955-2.419 2.157-2.419c1.21 0 2.176 1.096 2.157 2.42c0 1.333-.946 2.418-2.157 2.418z"/>
            </svg>
          </a>
          <a href="#" title="Twitter">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
              <path d="M23.953 4.57a10 10 0 01-2.825.775 4.958 4.958 0 002.163-2.723c-.951.555-2.005.959-3.127 1.184a4.92 4.92 0 00-8.384 4.482C7.69 8.095 4.067 6.13 1.64 3.162a4.822 4.822 0 00-.666 2.475c0 1.71.87 3.213 2.188 4.096a4.904 4.904 0 01-2.228-.616v.06a4.923 4.923 0 003.946 4.827 4.996 4.996 0 01-2.212.085 4.936 4.936 0 004.604 3.417 9.867 9.867 0 01-6.102 2.105c-.39 0-.779-.023-1.17-.067a13.995 13.995 0 007.557 2.209c9.053 0 13.998-7.496 13.998-13.985 0-.21 0-.42-.015-.63A9.935 9.935 0 0024 4.59z"/>
            </svg>
          </a>
          <a href="#" title="Instagram">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <rect x="2" y="2" width="20" height="20" rx="5" ry="5"/>
              <path d="M16 11.37A4 4 0 1 1 12.63 8 4 4 0 0 1 16 11.37z"/>
              <line x1="17.5" y1="6.5" x2="17.51" y2="6.5"/>
            </svg>
          </a>
          <a href="#" title="YouTube">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
              <path d="M23.498 6.186a3.016 3.016 0 0 0-2.122-2.136C19.505 3.545 12 3.545 12 3.545s-7.505 0-9.377.505A3.017 3.017 0 0 0 .502 6.186C0 8.07 0 12 0 12s0 3.93.502 5.814a3.016 3.016 0 0 0 2.122 2.136c1.871.505 9.376.505 9.376.505s7.505 0 9.377-.505a3.015 3.015 0 0 0 2.122-2.136C24 15.93 24 12 24 12s0-3.93-.502-5.814zM9.545 15.568V8.432L15.818 12l-6.273 3.568z"/>
            </svg>
          </a>
        </div>
      </div>
    </div>
    <div class="footer-bottom">
      <p>&copy; 2025 Nostrum Gaming. Todos los derechos reservados.</p>
    </div>
  </footer>

  <script src="/Nostrum/js/home.js"></script>
</body>
</html>
