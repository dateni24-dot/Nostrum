import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckCart {
    public static void main(String[] args) {
        String dbPath = "C:\\Users\\damasa\\Desktop\\apache-tomcat-10.1.49\\Nostrum.db";
        String DB_URL = "jdbc:sqlite:" + dbPath;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            
            // Verificar productes al carret de l'usuari 9
            String sql = "SELECT up.*, p.name FROM user_products up " +
                        "LEFT JOIN Product p ON up.product_id = p.id " +
                        "WHERE up.user_id = 9";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            System.out.println("\n=== PRODUCTES AL CARRET DE L'USUARI 9 ===");
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("ID: " + rs.getInt("product_id") + 
                                 " | Nom: " + rs.getString("name") + 
                                 " | Purchased: " + rs.getInt("purchased"));
            }
            
            if (count == 0) {
                System.out.println("❌ NO HI HA CAP PRODUCTE AL CARRET!");
            } else {
                System.out.println("\n✅ Total productes: " + count);
            }
            
            rs.close();
            ps.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
