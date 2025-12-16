import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TestDirecto {
    public static void main(String[] args) {
        System.out.println("=== Test Directo - Eliminar de user_products ===");
        
        String catalina = System.getProperty("catalina.base");
        String dbPath = (catalina != null) 
            ? "jdbc:sqlite:" + catalina + "/Nostrum.db"
            : "jdbc:sqlite:C:\\Users\\damasa\\Desktop\\apache-tomcat-10.1.49\\Nostrum.db";
        
        System.out.println("Conectando a: " + dbPath);
        
        String sql = "DELETE FROM user_products WHERE user_id = ? AND product_id = ?";
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(dbPath);
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, 9);
            ps.setInt(2, 6);
            int rows = ps.executeUpdate();
            
            System.out.println("✓ Eliminados " + rows + " registro(s)");
            
            ps.close();
            con.close();
            
        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
