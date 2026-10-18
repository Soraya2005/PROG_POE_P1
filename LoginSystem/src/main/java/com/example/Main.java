package com.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Registration ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cell phone number: ");
        String phone = scanner.nextLine();

        Login user = new Login(username, password, phone, firstName, lastName);

        String registrationMessage = user.registerUser();
        System.out.println(registrationMessage);

        String successMessage = "Username successfully captured.\n"
                              + "Password successfully captured.\n"
                              + "Cell number successfully added.";

        if (registrationMessage.equals(successMessage)) {

            System.out.println("\n=== Login ===");

            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            boolean loginSuccessful = user.loginUser(loginUsername, loginPassword);
            System.out.println(user.returnLoginStatus(loginSuccessful));

            // === QuickChat starts here ===
            if (loginSuccessful) {
                System.out.println("\nWelcome to QuickChat.");

                // Ask how many messages the user wants to send
                System.out.print("How many messages do you wish to enter? ");
                int numMessages = Integer.parseInt(scanner.nextLine());

                MessageManager manager = new MessageManager();
                int messagesProcessed = 0;

                boolean running = true;
                while (running) {
                    System.out.println("\n--- QuickChat Menu ---");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.print("Choose an option: ");
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1:
                            while (messagesProcessed < numMessages) {
                                // Ask for recipient
                                System.out.print("Enter recipient cell number: ");
                                String recipient = scanner.nextLine();

                                // Ask for message
                                System.out.print("Enter message: ");
                                String msgText = scanner.nextLine();

                                // Check message length
                                if (msgText.length() > 250) {
                                    System.out.println("Please enter a message of less than 250 characters.");
                                    continue;
                                }

                                // Create message
                                Message m = new Message(recipient, msgText);

                                // Check recipient format
                                String recipientCheck = m.checkRecipientCell();
                                if (!recipientCheck.equals("Cell phone number successfully captured.")) {
                                    System.out.println(recipientCheck);
                                    continue;
                                }

                                // Ask what to do
                                System.out.println("1) Send Message");
                                System.out.println("2) Disregard Message");
                                System.out.println("3) Store Message to send later");
                                System.out.print("Choose an option: ");
                                int action = Integer.parseInt(scanner.nextLine());

                                switch (action) {
                                    case 1:
                                        manager.addMessage(m);
                                        System.out.println("Message successfully sent.");
                                        System.out.println(m.printMessages());
                                        messagesProcessed++;
                                        break;
                                    case 2:
                                        System.out.println("Press 0 to delete the message.");
                                        scanner.nextLine();
                                        break;
                                    case 3:
                                        manager.addMessage(m);
                                        manager.storeMessage();
                                        System.out.println("Message successfully stored.");
                                        messagesProcessed++;
                                        break;
                                    default:
                                        System.out.println("Invalid option.");
                                }
                            }
                            System.out.println("Total messages sent: " + manager.returnTotalMessages());
                            break;

                        case 2:
                            System.out.println("Coming Soon.");
                            break;

                        case 3:
                            running = false;
                            System.out.println("Goodbye!");
                            break;

                        default:
                            System.out.println("Invalid option.");
                    }
                }
            }
        }

        scanner.close();
    }
}