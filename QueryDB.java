import java.sql.*;
public class QueryDB {
    public static void main(String[] args) {
        String dbPath = args[0];
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, username FROM Users ORDER BY id DESC LIMIT 10");
            System.out.println("\n=== USUARIOS REGISTRADOS (últimos 10) ===");
            int count = 0;
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | Usuario: " + rs.getString("username"));
                count++;
            }
            System.out.println("\nTotal mostrado: " + count);
            rs = stmt.executeQuery("SELECT COUNT(*) as total FROM Users");
            if (rs.next()) {
                System.out.println("Total en base de datos: " + rs.getInt("total"));
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
