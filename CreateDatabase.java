import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateDatabase {
    public static void main(String[] args) {
        String dbPath = "C:\\Users\\damasa\\Desktop\\Nostrum-1\\Nostrum.db";
        String DB_URL = "jdbc:sqlite:" + dbPath;

        try {
            // Cargar driver SQLite
            Class.forName("org.sqlite.JDBC");
            System.out.println("✓ Driver SQLite cargado");

            // Conectar (crea la BD si no existe)
            Connection conn = DriverManager.getConnection(DB_URL);
            System.out.println("✓ Base de datos creada: " + dbPath);

            // Crear tabla Users
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                    "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  username TEXT UNIQUE NOT NULL," +
                    "  password TEXT NOT NULL" +
                    ")";
            stmt.execute(sql);
            System.out.println("✓ Tabla Users creada");

            // Crear índice
            String indexSql = "CREATE INDEX IF NOT EXISTS idx_username ON Users(username)";
            stmt.execute(indexSql);
            System.out.println("✓ Índice idx_username creado");

            // Verificar tabla
            String checkSql = "SELECT name FROM sqlite_master WHERE type='table' AND name='Users'";
            var rs = stmt.executeQuery(checkSql);
            if (rs.next()) {
                System.out.println("✓ Tabla Users verificada correctamente");
            }

            stmt.close();
            conn.close();
            System.out.println("\n✅ Base de datos configurada exitosamente en: " + dbPath);

        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: Driver SQLite no encontrado");
            System.err.println("Asegúrate de que sqlite-jdbc está en el pom.xml");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
