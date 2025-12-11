import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckProductsTomcat {
    public static void main(String[] args) {
        String dbPath = "C:\\Users\\damasa\\Desktop\\apache-tomcat-10.1.49\\Nostrum.db";
        String DB_URL = "jdbc:sqlite:" + dbPath;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            
            System.out.println("=== PRODUCTOS EN BASE DE DATOS DE TOMCAT ===\n");
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Product";
            ResultSet rs = stmt.executeQuery(sql);
            
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println(count + ". ID: " + rs.getInt("id"));
                System.out.println("   Nombre: " + rs.getString("name"));
                System.out.println("   Precio: " + rs.getDouble("price"));
                System.out.println("   Imagen: " + rs.getString("image_url"));
                System.out.println();
            }
            
            System.out.println("✅ Total: " + count + " productos");
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
