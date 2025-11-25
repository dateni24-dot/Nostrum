import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckUsers {
    public static void main(String[] args) {
        String dbPath = "C:\\Users\\damasa\\Desktop\\Nostrum-1\\Nostrum.db";
        String DB_URL = "jdbc:sqlite:" + dbPath;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            
            System.out.println("\n=== USUARIOS REGISTRADOS ===\n");
            
            Statement stmt = conn.createStatement();
            String sql = "SELECT id, username FROM Users ORDER BY id DESC";
            ResultSet rs = stmt.executeQuery(sql);
            
            int count = 0;
            while (rs.next()) {
                count++;
                int id = rs.getInt("id");
                String username = rs.getString("username");
                System.out.println(count + ". ID: " + id + " | Usuario: " + username);
            }
            
            if (count == 0) {
                System.out.println("❌ No hay usuarios registrados");
            } else {
                System.out.println("\n✅ Total de usuarios: " + count);
            }
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
