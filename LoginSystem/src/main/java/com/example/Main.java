package com.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Registration ===");

        // Ask for first and last name (needed for the welcome message)
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        // Ask for login information
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cell phone number: ");
        String phone = scanner.nextLine();

        // Create the user with the 5-parameter constructor
        Login user = new Login(username, password, phone, firstName, lastName);

        // Attempt registration
        String registrationMessage = user.registerUser();
        System.out.println(registrationMessage);

        // Exact success message 
        String successMessage = "Username successfully captured.\n"
                              + "Password successfully captured.\n"
                              + "Cell number successfully added.";

        // If registration succeeded, move on to login
        if (registrationMessage.equals(successMessage)) {

            System.out.println("\n=== Login ===");

            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            boolean loginSuccessful =
                    user.loginUser(loginUsername, loginPassword);

            System.out.println(user.returnLoginStatus(loginSuccessful));
        }

        scanner.close();
    }
}