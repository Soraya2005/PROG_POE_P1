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

            if (loginSuccessful) {
                System.out.println("\nWelcome to QuickChat.");

                // Update the previously stored messages at startup
                MessageArrayManager.loadMessagesFromJson();

                System.out.print("How many messages do you wish to enter? ");
                int numMessages = Integer.parseInt(scanner.nextLine());
                int messagesProcessed = 0;

                boolean running = true;
                while (running) {
                    System.out.println("\n--- QuickChat Menu ---");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.println("4) Stored Messages");
                    System.out.print("Choose an option: ");
                    String choice = scanner.nextLine();

                    switch (choice) {
                        case "1":
                            if (messagesProcessed >= numMessages) {
                                System.out.println("You have already entered all " + numMessages + " messages.");
                                break;
                            }

                            while (messagesProcessed < numMessages) {
                                System.out.print("Enter recipient cell number: ");
                                String recipient = scanner.nextLine();

                                System.out.print("Enter message: ");
                                String msgText = scanner.nextLine();

                                if (msgText.length() > 250) {
                                    System.out.println("Please enter a message of less than 250 characters.");
                                    continue;
                                }

                                Message m = new Message(recipient, msgText);

                                String recipientCheck = m.checkRecipientCell();
                                if (!recipientCheck.equals("Cell phone number successfully captured.")) {
                                    System.out.println(recipientCheck);
                                    continue;
                                }

                                System.out.println("1) Send Message");
                                System.out.println("2) Disregard Message");
                                System.out.println("3) Store Message to send later");
                                System.out.print("Choose an option: ");
                                String action = scanner.nextLine();

                                switch (action) {
                                    case "1":
                                        m.setNumMessagesSent(MessageArrayManager.sentCount + 1);
                                        MessageArrayManager.addSentMessage(m);
                                        System.out.println("Message successfully sent.");
                                        System.out.println(m.printMessages());
                                        messagesProcessed++;
                                        break;
                                    case "2":
                                        MessageArrayManager.addDisregardedMessage(m);
                                        System.out.println("Press 0 to delete the message.");
                                        scanner.nextLine();
                                        messagesProcessed++;
                                        break;
                                    case "3":
                                        MessageArrayManager.addStoredMessage(m);
                                        System.out.println("Message successfully stored.");
                                        messagesProcessed++;
                                        break;
                                    default:
                                        System.out.println("Invalid option.");
                                }
                            }
                            break;

                        case "2":
                            System.out.println("Coming Soon.");
                            break;

                        case "3":
                            running = false;
                            System.out.println("Goodbye!");
                            break;

                        case "4":
                            StoredMessagesMenu.showMenu(scanner, firstName + " " + lastName);
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