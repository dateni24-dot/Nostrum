package org.yourcompany.yourproject;
<<<<<<< HEAD:Nostrum/Basura/java-backend-innecesario/RunApp.java

// Placeholder main class removed Spring Boot to avoid compilation errors
public class RunApp {
    // Intentionally empty when deploying to external Tomcat
=======
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunApp {

    public static void main(String[] args) {
        SpringApplication.run(RunApp.class, args);
    }
>>>>>>> 840543f1961ba1e965d97b138e5337924366e23e:Nostrum/src/main/java/org/yourcompany/yourproject/RunApp.java
}