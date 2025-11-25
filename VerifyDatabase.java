import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class VerifyDatabase {
    public static void main(String[] args) {
        String dbPath = "C:\\Users\\damasa\\Desktop\\Nostrum-1\\Nostrum.db";
        String DB_URL = "jdbc:sqlite:" + dbPath;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            
            System.out.println("✓ Conectado a: " + dbPath);
            
            Statement stmt = conn.createStatement();
            
            // Verificar tablas
            String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='Users'";
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                System.out.println("✓ Tabla Users existe");
                
                // Contar usuarios
                String countSql = "SELECT COUNT(*) as count FROM Users";
                ResultSet countRs = stmt.executeQuery(countSql);
                if (countRs.next()) {
                    int count = countRs.getInt("count");
                    System.out.println("✓ Usuarios registrados: " + count);
                }
            } else {
                System.out.println("❌ Tabla Users NO existe");
            }
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
