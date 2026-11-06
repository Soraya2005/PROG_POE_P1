package com.example;

import java.util.Scanner;

public class StoredMessagesMenu {

    public static void showMenu(Scanner scanner) {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("1) Display sender and recipient of all stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search for messages for a particular recipient");
            System.out.println("5) Delete a message using the message hash");
            System.out.println("6) Display full report");
            System.out.println("7) Back to Main Menu");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    for (int i = 0; i < MessageArrayManager.storedCount; i++) {
                        if (MessageArrayManager.storedMessages[i] != null) {
                            System.out.println("Sender: Me | Recipient: " + MessageArrayManager.storedMessages[i].getRecipient());
                        }
                    }
                    break;
                case "2":
                    displayLongestMessage();
                    break;
                case "3":
                    System.out.print("Enter Message ID to search: ");
                    searchByID(scanner.nextLine());
                    break;
                case "4":
                    System.out.print("Enter Recipient to search: ");
                    searchByRecipient(scanner.nextLine());
                    break;
                case "5":
                    System.out.print("Enter Message Hash to delete: ");
                    deleteByHash(scanner.nextLine());
                    break;
                case "6":
                    displayReport();
                    break;
                case "7":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void displayLongestMessage() {
        String longest = "";
        for (int i = 0; i < MessageArrayManager.storedCount; i++) {
            if (MessageArrayManager.storedMessages[i] != null) {
                if (MessageArrayManager.storedMessages[i].getMessageText().length() > longest.length()) {
                    longest = MessageArrayManager.storedMessages[i].getMessageText();
                }
            }
        }
        System.out.println("Longest message: " + longest);
    }

    private static void searchByID(String id) {
        for (int i = 0; i < MessageArrayManager.storedCount; i++) {
            if (MessageArrayManager.storedMessages[i] != null && MessageArrayManager.storedMessages[i].getMessageID().equals(id)) {
                System.out.println("Recipient: " + MessageArrayManager.storedMessages[i].getRecipient());
                System.out.println("Message: " + MessageArrayManager.storedMessages[i].getMessageText());
                return;
            }
        }
        System.out.println("Message ID not found.");
    }

    private static void searchByRecipient(String recipient) {
        boolean found = false;
        for (int i = 0; i < MessageArrayManager.storedCount; i++) {
            if (MessageArrayManager.storedMessages[i] != null && MessageArrayManager.storedMessages[i].getRecipient().equals(recipient)) {
                System.out.println("Message: " + MessageArrayManager.storedMessages[i].getMessageText());
                found = true;
            }
        }
        if (!found) System.out.println("No messages found for this recipient.");
    }

    private static void deleteByHash(String hash) {
        for (int i = 0; i < MessageArrayManager.storedCount; i++) {
            if (MessageArrayManager.storedMessages[i] != null && MessageArrayManager.storedMessages[i].getMessageHash().equals(hash)) {
                String deletedText = MessageArrayManager.storedMessages[i].getMessageText();
                // On décale tout le tableau pour supprimer l'élément
                for (int j = i; j < MessageArrayManager.storedCount - 1; j++) {
                    MessageArrayManager.storedMessages[j] = MessageArrayManager.storedMessages[j + 1];
                }
                MessageArrayManager.storedMessages[MessageArrayManager.storedCount - 1] = null;
                MessageArrayManager.storedCount--;
                MessageArrayManager.saveMessagesToJson();
                System.out.println("Message: \"" + deletedText + "\" successfully deleted.");
                return;
            }
        }
        System.out.println("Message hash not found.");
    }

    private static void displayReport() {
        System.out.println("\n=== REPORT ===");
        for (int i = 0; i < MessageArrayManager.storedCount; i++) {
            if (MessageArrayManager.storedMessages[i] != null) {
                System.out.println("Message Hash: " + MessageArrayManager.storedMessages[i].getMessageHash());
                System.out.println("Recipient: " + MessageArrayManager.storedMessages[i].getRecipient());
                System.out.println("Message: " + MessageArrayManager.storedMessages[i].getMessageText());
                System.out.println("-------------------------");
            }
        }
    }
}