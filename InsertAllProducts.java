import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertAllProducts {
    public static void main(String[] args) {
        String dbPath = "C:\\Users\\damasa\\Desktop\\apache-tomcat-10.1.49\\Nostrum.db";
        String DB_URL = "jdbc:sqlite:" + dbPath;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            
            String deleteSql = "DELETE FROM Product WHERE id > 1";
            PreparedStatement deletePs = conn.prepareStatement(deleteSql);
            deletePs.executeUpdate();
            deletePs.close();
            System.out.println("✅ Productos anteriores eliminados\n");
            
            String sql = "INSERT INTO Product (name, description, image_url, price) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            // RATONES
            ps.setString(1, "Logitech G502 HERO");
            ps.setString(2, "Ratón gaming de alto rendimiento con sensor HERO 25K");
            ps.setString(3, "/Nostrum/imagenes/RatonLogitech.png");
            ps.setDouble(4, 79.99);
            ps.executeUpdate();
            System.out.println("✅ Logitech G502 HERO");
            
            ps.setString(1, "Razer DeathAdder V3");
            ps.setString(2, "Ratón gaming ergonómico con tecnología HyperSpeed");
            ps.setString(3, "/Nostrum/imagenes/RatonRazer.png");
            ps.setDouble(4, 89.99);
            ps.executeUpdate();
            System.out.println("✅ Razer DeathAdder V3");
            
            // TECLADOS
            ps.setString(1, "HyperX Alloy Origins RGB");
            ps.setString(2, "Teclado mecánico RGB con switches HyperX Red");
            ps.setString(3, "/Nostrum/imagenes/TecladoSteelseries.png");
            ps.setDouble(4, 109.99);
            ps.executeUpdate();
            System.out.println("✅ HyperX Alloy Origins RGB");
            
            ps.setString(1, "Razer Huntsman Mini");
            ps.setString(2, "Teclado mecánico compacto 60% con switches ópticos");
            ps.setString(3, "/Nostrum/imagenes/Techadorazer.png");
            ps.setDouble(4, 119.99);
            ps.executeUpdate();
            System.out.println("✅ Razer Huntsman Mini");
            
            // AURICULARES
            ps.setString(1, "HyperX Cloud II");
            ps.setString(2, "Auriculares gaming con sonido envolvente 7.1");
            ps.setString(3, "/Nostrum/imagenes/HeadsetHyperX.png");
            ps.setDouble(4, 99.99);
            ps.executeUpdate();
            System.out.println("✅ HyperX Cloud II");
            
            ps.setString(1, "SteelSeries Arctis 7");
            ps.setString(2, "Auriculares inalámbricos gaming con DTS Headphone:X");
            ps.setString(3, "/Nostrum/imagenes/CascosSteelseries.png");
            ps.setDouble(4, 149.99);
            ps.executeUpdate();
            System.out.println("✅ SteelSeries Arctis 7");
            
            // HARDWARE
            ps.setString(1, "RTX 4070");
            ps.setString(2, "Tarjeta gráfica NVIDIA GeForce RTX 4070 12GB GDDR6X");
            ps.setString(3, "/Nostrum/imagenes/RTX4070.png");
            ps.setDouble(4, 649.99);
            ps.executeUpdate();
            System.out.println("✅ RTX 4070");
            
            ps.setString(1, "SSD NVMe 1TB");
            ps.setString(2, "Disco SSD NVMe PCIe 4.0 de alta velocidad 1TB");
            ps.setString(3, "/Nostrum/imagenes/ssd.png");
            ps.setDouble(4, 89.99);
            ps.executeUpdate();
            System.out.println("✅ SSD NVMe 1TB");
            
            // FIGURAS
            ps.setString(1, "Funko Pop The Witcher 3");
            ps.setString(2, "Figura coleccionable Funko Pop Geralt de Rivia");
            ps.setString(3, "/Nostrum/imagenes/FunkopopWitcher.png");
            ps.setDouble(4, 14.99);
            ps.executeUpdate();
            System.out.println("✅ Funko Pop The Witcher 3");
            
            ps.setString(1, "Funko Pop Elden Ring");
            ps.setString(2, "Figura coleccionable Funko Pop Elden Ring");
            ps.setString(3, "/Nostrum/imagenes/FunkopopEldenring.png");
            ps.setDouble(4, 14.99);
            ps.executeUpdate();
            System.out.println("✅ Funko Pop Elden Ring");
            
            ps.close();
            conn.close();
            
            System.out.println("\n✅ Todos los productos insertados correctamente en la BD de Tomcat!");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
