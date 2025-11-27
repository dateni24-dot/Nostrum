// ========== HOME PAGE JAVASCRIPT ==========

// ========== Session Management ==========
window.addEventListener('DOMContentLoaded', () => {
  loadUsername();
  initCarousel();
  initSearch();
  initAddToCart();
  initCategoryNavigation();
});

// Load username from session
async function loadUsername() {
  try {
    // Try to get username from session attribute
    const username = sessionStorage.getItem('usuario');
    const usernameElement = document.getElementById('username');
    
    if (username && usernameElement) {
      usernameElement.textContent = `Hola, ${username}`;
    } else if (usernameElement) {
      // If no session, redirect to login
      window.location.href = '/Nostrum/html/login.html';
    }
  } catch (error) {
    console.error('Error loading username:', error);
  }
}

// ========== Carousel Functionality ==========
let currentSlide = 0;
let autoSlideInterval;
const SLIDE_INTERVAL = 4000; // 4 seconds

function initCarousel() {
  const carousel = document.getElementById('popularCarousel');
  const cards = carousel.querySelectorAll('.product-card');
  const cardsToShow = window.innerWidth <= 768 ? 1 : 3;
  
  // Set animation delay for entrance
  cards.forEach((card, index) => {
    card.style.animationDelay = `${index * 0.1}s`;
  });
  
  // Start auto-slide
  startAutoSlide();
  
  // Pause on hover
  carousel.addEventListener('mouseenter', () => {
    stopAutoSlide();
  });
  
  carousel.addEventListener('mouseleave', () => {
    startAutoSlide();
  });
  
  // Handle window resize
  window.addEventListener('resize', () => {
    const newCardsToShow = window.innerWidth <= 768 ? 1 : 3;
    if (newCardsToShow !== cardsToShow) {
      currentSlide = 0;
      updateCarousel();
    }
  });
}

function scrollCarousel(direction) {
  const carousel = document.getElementById('popularCarousel');
  const cards = carousel.querySelectorAll('.product-card');
  const cardsToShow = window.innerWidth <= 768 ? 1 : 3;
  const maxSlide = Math.max(0, cards.length - cardsToShow);
  
  // Update current slide
  currentSlide += direction;
  
  // Loop around
  if (currentSlide < 0) {
    currentSlide = maxSlide;
  } else if (currentSlide > maxSlide) {
    currentSlide = 0;
  }
  
  updateCarousel();
  
  // Reset auto-slide timer
  stopAutoSlide();
  startAutoSlide();
}

function updateCarousel() {
  const carousel = document.getElementById('popularCarousel');
  const cards = carousel.querySelectorAll('.product-card');
  const cardWidth = cards[0].offsetWidth;
  const gap = 24; // 1.5rem = 24px
  
  const offset = -(currentSlide * (cardWidth + gap));
  carousel.style.transform = `translateX(${offset}px)`;
}

function startAutoSlide() {
  autoSlideInterval = setInterval(() => {
    scrollCarousel(1);
  }, SLIDE_INTERVAL);
}

function stopAutoSlide() {
  if (autoSlideInterval) {
    clearInterval(autoSlideInterval);
  }
}

// ========== Search Functionality ==========
function initSearch() {
  const searchInput = document.getElementById('searchInput');
  const searchBtn = document.querySelector('.search-btn');
  
  // Handle search button click
  searchBtn.addEventListener('click', performSearch);
  
  // Handle Enter key
  searchInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
      performSearch();
    }
  });
  
  // Live search suggestions (future feature)
  let searchTimeout;
  searchInput.addEventListener('input', (e) => {
    clearTimeout(searchTimeout);
    searchTimeout = setTimeout(() => {
      // Future: Show search suggestions
      console.log('Searching for:', e.target.value);
    }, 300);
  });
}

function performSearch() {
  const searchInput = document.getElementById('searchInput');
  const query = searchInput.value.trim();
  
  if (query.length > 0) {
    // Future: Navigate to search results page
    console.log('Performing search for:', query);
    alert(`🔍 Buscando: "${query}"\n\n(Página de resultados en desarrollo)`);
  }
}

// ========== Add to Cart Functionality ==========
let cartCount = 0;

function initAddToCart() {
  const addButtons = document.querySelectorAll('.add-cart-btn');
  
  addButtons.forEach(button => {
    button.addEventListener('click', (e) => {
      e.stopPropagation(); // Prevent card click
      
      const card = button.closest('.product-card');
      const productName = card.querySelector('h3').textContent;
      const productPrice = card.querySelector('.price').textContent;
      
      addToCart(productName, productPrice);
      
      // Visual feedback
      button.style.transform = 'rotate(90deg) scale(1.2)';
      setTimeout(() => {
        button.style.transform = '';
      }, 300);
    });
  });
  
  // Handle product card click for details
  const productCards = document.querySelectorAll('.product-card');
  productCards.forEach(card => {
    card.addEventListener('click', (e) => {
      // Don't trigger if clicking add-to-cart button
      if (!e.target.closest('.add-cart-btn')) {
        const productName = card.querySelector('h3').textContent;
        showProductDetails(productName);
      }
    });
  });
}

function addToCart(productName, productPrice) {
  cartCount++;
  updateCartBadge();
  
  // Future: Save to localStorage or send to backend
  console.log('Added to cart:', productName, productPrice);
  
  // Show notification
  showNotification(`✅ ${productName} añadido al carrito`);
}

function updateCartBadge() {
  const badge = document.querySelector('.cart-badge');
  if (badge) {
    badge.textContent = cartCount;
    
    // Animation
    badge.style.transform = 'scale(1.3)';
    setTimeout(() => {
      badge.style.transform = 'scale(1)';
    }, 200);
  }
}

function showNotification(message) {
  // Create notification element
  const notification = document.createElement('div');
  notification.textContent = message;
  notification.style.cssText = `
    position: fixed;
    top: 90px;
    right: 20px;
    background: linear-gradient(135deg, rgba(0, 179, 255, 0.95), rgba(123, 46, 255, 0.95));
    color: white;
    padding: 1rem 1.5rem;
    border-radius: 12px;
    font-weight: 600;
    z-index: 10000;
    box-shadow: 0 10px 30px rgba(0, 179, 255, 0.4);
    animation: slideInRight 0.3s ease, slideOutRight 0.3s ease 2.7s;
  `;
  
  document.body.appendChild(notification);
  
  // Remove after animation
  setTimeout(() => {
    notification.remove();
  }, 3000);
}

function showProductDetails(productName) {
  // Future: Navigate to product detail page
  console.log('Showing details for:', productName);
  alert(`📦 ${productName}\n\n(Página de detalles en desarrollo)`);
}

// ========== Category Navigation ==========
function initCategoryNavigation() {
  const categoryCards = document.querySelectorAll('.category-card');
  
  categoryCards.forEach(card => {
    card.addEventListener('click', () => {
      const categoryName = card.querySelector('h3').textContent;
      navigateToCategory(categoryName);
    });
  });
}

function navigateToCategory(categoryName) {
  // Future: Navigate to category page
  console.log('Navigating to category:', categoryName);
  alert(`🎮 Categoría: ${categoryName}\n\n(Página de categoría en desarrollo)`);
}

// ========== News Items ==========
const newsItems = document.querySelectorAll('.news-item');
newsItems.forEach(item => {
  item.addEventListener('click', () => {
    const title = item.querySelector('h3').textContent;
    console.log('Reading news:', title);
    alert(`📰 ${title}\n\n(Artículo completo en desarrollo)`);
  });
});

// ========== Animations ==========
// Add keyframes for notification animations
const style = document.createElement('style');
style.textContent = `
  @keyframes slideInRight {
    from {
      transform: translateX(400px);
      opacity: 0;
    }
    to {
      transform: translateX(0);
      opacity: 1;
    }
  }
  
  @keyframes slideOutRight {
    from {
      transform: translateX(0);
      opacity: 1;
    }
    to {
      transform: translateX(400px);
      opacity: 0;
    }
  }
`;
document.head.appendChild(style);

// ========== Scroll Effects ==========
window.addEventListener('scroll', () => {
  const header = document.querySelector('.main-header');
  const searchContainer = document.querySelector('.search-container');
  
  if (window.scrollY > 50) {
    header.style.background = 'rgba(10, 10, 30, 0.95)';
    searchContainer.style.background = 'rgba(5, 5, 20, 0.95)';
  } else {
    header.style.background = 'rgba(10, 10, 30, 0.85)';
    searchContainer.style.background = 'rgba(5, 5, 20, 0.8)';
  }
});

// ========== Performance Optimization ==========
// Lazy load images
if ('IntersectionObserver' in window) {
  const imageObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        const img = entry.target;
        if (img.dataset.src) {
          img.src = img.dataset.src;
          img.removeAttribute('data-src');
          imageObserver.unobserve(img);
        }
      }
    });
  });
  
  document.querySelectorAll('img[data-src]').forEach(img => {
    imageObserver.observe(img);
  });
}

// ========== Debug Info ==========
console.log('🎮 Nostrum Gaming Home - Loaded');
console.log('Current user:', sessionStorage.getItem('usuario'));
console.log('Cart items:', cartCount);
