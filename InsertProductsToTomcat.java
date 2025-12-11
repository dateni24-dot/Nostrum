import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertProductsToTomcat {
    public static void main(String[] args) {
        String dbPath = "C:\\Users\\damasa\\Desktop\\apache-tomcat-10.1.49\\Nostrum.db";
        String DB_URL = "jdbc:sqlite:" + dbPath;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            
            String sql = "INSERT INTO Product (name, description, image_url, price) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            // Producto 1
            ps.setString(1, "The Witcher 3: Wild Hunt");
            ps.setString(2, "Juego de rol de mundo abierto con una historia épica");
            ps.setString(3, "/Nostrum/images/witcher3.jpg");
            ps.setDouble(4, 29.99);
            ps.executeUpdate();
            System.out.println("✅ Producto 1 insertado: The Witcher 3");
            
            // Producto 2
            ps.setString(1, "Cyberpunk 2077");
            ps.setString(2, "RPG de acción futurista en Night City");
            ps.setString(3, "/Nostrum/images/cyberpunk.jpg");
            ps.setDouble(4, 39.99);
            ps.executeUpdate();
            System.out.println("✅ Producto 2 insertado: Cyberpunk 2077");
            
            // Producto 3
            ps.setString(1, "Red Dead Redemption 2");
            ps.setString(2, "Aventura épica del Salvaje Oeste");
            ps.setString(3, "/Nostrum/images/rdr2.jpg");
            ps.setDouble(4, 49.99);
            ps.executeUpdate();
            System.out.println("✅ Producto 3 insertado: Red Dead Redemption 2");
            
            ps.close();
            conn.close();
            
            System.out.println("\n✅ Todos los productos insertados en la BD de Tomcat!");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
