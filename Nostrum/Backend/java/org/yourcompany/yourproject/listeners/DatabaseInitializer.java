package org.yourcompany.yourproject.listeners;

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
        String catalina = System.getProperty("catalina.base");
        String dbPath = (catalina != null) 
            ? "jdbc:sqlite:" + catalina + "/Nostrum.db"
            : "jdbc:sqlite:Nostrum.db";

        try (Connection conn = DriverManager.getConnection(dbPath);
             Statement stmt = conn.createStatement()) {
            
            String createTable = "CREATE TABLE IF NOT EXISTS Users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT UNIQUE NOT NULL, " +
                    "password TEXT NOT NULL)";
            stmt.execute(createTable);

            String createIndex = "CREATE INDEX IF NOT EXISTS idx_username ON Users(username)";
            stmt.execute(createIndex);

            System.out.println("✓ Database initialized: " + dbPath);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup si es necesario
    }
}
