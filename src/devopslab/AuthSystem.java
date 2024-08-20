package devopslab;

import java.io.*;
import java.util.Scanner;

public class AuthSystem {
    private static final String FILE_NAME = "users.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    signUp(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        } while (choice != 3);

        scanner.close();
    }

    private static void signUp(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (isUserExist(username)) {
            System.out.println("Username already exists. Try again.");
        } else {
            saveUser(username, password);
            System.out.println("Sign-up successful!");
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (authenticate(username, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static boolean isUserExist(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking username: " + e.getMessage());
        }
        return false;
    }

    private static void saveUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    private static boolean authenticate(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
        }
        return false;
    }
}
