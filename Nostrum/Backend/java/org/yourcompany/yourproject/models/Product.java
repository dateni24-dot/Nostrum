package org.yourcompany.yourproject.models;

public class Product {
    private int id;
    private String name;
    private String description;
    private String image_url;
        private double price;

    public Product() {}

    public Product(int id, String name, String description, String image_url, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.price = price;
    }

    // Getters y Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public double getPrice() { 
        return price; 
    }
    
    public void setPrice(double price) { 
        this.price = price; 
    }
    
    public String getDescription() { 
        return description; 
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
}
