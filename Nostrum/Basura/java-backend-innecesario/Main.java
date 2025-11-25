package org.yourcompany.yourproject;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Nostrum nostrum = new Nostrum();
    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("Bienvenido a Nostrum");
            System.out.println();
            System.out.println("1. Crear usuario");
            System.out.println("2. Leer usuarios");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Salir");
            System.out.println();
            System.out.print("Seleccione una opción: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Ingrese el nombre de usuario: ");
                    String username = scanner.next();
                    System.out.print("Ingrese la contraseña (número): ");
                    String password = scanner.next();
                    createUser(username, password);
                    break;
                case 2:
                    System.out.println("Lista de usuarios:");
                    System.out.println();
                    readUsers();
                    break;
                case 3:
                    System.out.print("Ingrese el ID del usuario a actualizar: ");
                    int idToUpdate = scanner.nextInt();
                    System.out.print("Ingrese el nuevo nombre de usuario: ");
                    String newUsername = scanner.next();
                    System.out.print("Ingrese la nueva contraseña: ");
                    String newPassword = scanner.next();
                    updateUser(idToUpdate, newUsername, newPassword);
                    break;
                case 4:
                    System.out.print("Ingrese el ID del usuario a eliminar: ");
                    int idToDelete = scanner.nextInt();
                    deleteUser(idToDelete);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
    public static void createUser(String username, String password) {
        nostrum.createUser(username, password);
    }
    public static void readUsers() {
        nostrum.readUsers();
    }
    public static void updateUser(int id, String username, String password) {
        nostrum.updateUser(id, username, password);
    }
    public static void deleteUser(int id) {
        nostrum.deleteUser(id);
    }


    
}
