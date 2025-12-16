import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckUserProducts {
    public static void main(String[] args) {
        String dbPath = "C:\\Users\\damasa\\Desktop\\apache-tomcat-10.1.49\\Nostrum.db";
        String DB_URL = "jdbc:sqlite:" + dbPath;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            
            System.out.println("=== PRODUCTOS EN CARRITO (user_products) ===\n");
            Statement stmt = conn.createStatement();
            String sql = "SELECT up.user_id, up.product_id, up.purchased, p.name " +
                        "FROM user_products up " +
                        "LEFT JOIN Product p ON up.product_id = p.id " +
                        "ORDER BY up.user_id";
            ResultSet rs = stmt.executeQuery(sql);
            
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println(count + ". User ID: " + rs.getInt("user_id"));
                System.out.println("   Product ID: " + rs.getInt("product_id"));
                System.out.println("   Purchased: " + rs.getInt("purchased"));
                System.out.println("   Producto: " + rs.getString("name"));
                System.out.println();
            }
            
            if (count == 0) {
                System.out.println("❌ No hay productos en carritos");
            } else {
                System.out.println("✅ Total: " + count + " productos en carritos");
            }
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
