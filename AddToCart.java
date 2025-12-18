import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddToCart {
    public static void main(String[] args) {
        String dbPath = "C:\\Users\\damasa\\Desktop\\apache-tomcat-10.1.49\\Nostrum.db";
        String DB_URL = "jdbc:sqlite:" + dbPath;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            
            String sql = "INSERT INTO user_products (user_id, product_id, purchased) VALUES (?, ?, 0)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            // Afegir Razer DeathAdder V3 (id=2)
            ps.setInt(1, 9);  // user_id
            ps.setInt(2, 2);  // product_id
            ps.executeUpdate();
            System.out.println("✅ Razer DeathAdder V3 afegit al carret");
            
            // Afegir HyperX Cloud II (id=5) - Primera unitat
            ps.setInt(1, 9);
            ps.setInt(2, 5);
            ps.executeUpdate();
            System.out.println("✅ HyperX Cloud II (1) afegit al carret");
            
            // Afegir HyperX Cloud II (id=5) - Segona unitat
            ps.setInt(1, 9);
            ps.setInt(2, 5);
            ps.executeUpdate();
            System.out.println("✅ HyperX Cloud II (2) afegit al carret");
            
            // Afegir RTX 4070 (id=7)
            ps.setInt(1, 9);
            ps.setInt(2, 7);
            ps.executeUpdate();
            System.out.println("✅ RTX 4070 afegit al carret");
            
            // Afegir Funko Pop Witcher (id=9)
            ps.setInt(1, 9);
            ps.setInt(2, 9);
            ps.executeUpdate();
            System.out.println("✅ Funko Pop Witcher afegit al carret");
            
            ps.close();
            conn.close();
            
            System.out.println("\n✅ Tots els productes afegits al carret de l'usuari 9!");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
