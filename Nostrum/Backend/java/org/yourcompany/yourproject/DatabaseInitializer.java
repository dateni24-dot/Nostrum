package org.yourcompany.yourproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("🔄 Inicializando base de datos SQLite...");
        
        try {
            // Cargar driver SQLite
            Class.forName("org.sqlite.JDBC");
            
            // Obtener ruta relativa al servidor
            String dbPath = System.getProperty("catalina.base") != null 
                ? System.getProperty("catalina.base") + "/Nostrum.db"
                : "Nostrum.db";
            
            String DB_URL = "jdbc:sqlite:" + dbPath;
            
            // Conectar (crea la BD si no existe)
            Connection conn = DriverManager.getConnection(DB_URL);
            System.out.println("✓ Base de datos conectada: " + dbPath);

            // Crear tabla Users
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                    "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  username TEXT UNIQUE NOT NULL," +
                    "  password TEXT NOT NULL" +
                    ")";
            stmt.execute(sql);
            System.out.println("✓ Tabla Users verificada");

            // Crear índice
            String indexSql = "CREATE INDEX IF NOT EXISTS idx_username ON Users(username)";
            stmt.execute(indexSql);
            System.out.println("✓ Índice idx_username verificado");

            stmt.close();
            conn.close();
            
            System.out.println("✅ Base de datos SQLite lista en: " + dbPath);

        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: Driver SQLite no encontrado");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Error inicializando BD: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("🛑 Aplicación cerrada");
    }
}
