package com.example;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MessageArrayManager {

    // Tables
    public static Message[] sentMessages = new Message[100];
    public static Message[] disregardedMessages = new Message[100];
    public static Message[] storedMessages = new Message[100];
    public static String[] messageHashes = new String[100];
    public static String[] messageIDs = new String[100];

    public static int sentCount = 0;
    public static int disregardedCount = 0;
    public static int storedCount = 0;
    public static int hashCount = 0;
    public static int idCount = 0;

    // Reset the arrays
    public static void reset() {
        sentMessages = new Message[100];
        disregardedMessages = new Message[100];
        storedMessages = new Message[100];
        messageHashes = new String[100];
        messageIDs = new String[100];
        sentCount = 0;
        disregardedCount = 0;
        storedCount = 0;
        hashCount = 0;
        idCount = 0;
    }

    // Add a sent message
    public static void addSentMessage(Message m) {
        sentMessages[sentCount++] = m;
        messageHashes[hashCount++] = m.getMessageHash();
        messageIDs[idCount++] = m.getMessageID();
    }

    // Add an ignored message
    public static void addDisregardedMessage(Message m) {
        disregardedMessages[disregardedCount++] = m;
        messageHashes[hashCount++] = m.getMessageHash();
        messageIDs[idCount++] = m.getMessageID();
    }

    // Add a stored message
    public static void addStoredMessage(Message m) {
        storedMessages[storedCount++] = m;
        messageHashes[hashCount++] = m.getMessageHash();
        messageIDs[idCount++] = m.getMessageID();
        saveMessagesToJson();
    }

    // Save the stored messages to the JSON file
    public static void saveMessagesToJson() {
        JSONArray arr = new JSONArray();
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i] != null) {
                JSONObject obj = new JSONObject();
                obj.put("MessageID", storedMessages[i].getMessageID());
                obj.put("MessageHash", storedMessages[i].getMessageHash());
                obj.put("Recipient", storedMessages[i].getRecipient());
                obj.put("Message", storedMessages[i].getMessageText());
                arr.put(obj);
            }
        }
        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(arr.toString(4));
        } catch (IOException e) {
            System.out.println("Error saving JSON: " + e.getMessage());
        }
    }

    // Read the JSON file without duplicating existing entries
    public static void loadMessagesFromJson() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("messages.json")));
            JSONArray arr = new JSONArray(content);
            storedMessages = new Message[100];
            storedCount = 0;
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String recipient = obj.getString("Recipient");
                String text = obj.getString("Message");
                Message m = new Message(recipient, text);
                storedMessages[storedCount++] = m;
                messageHashes[hashCount++] = m.getMessageHash();
                messageIDs[idCount++] = m.getMessageID();
            }
        } catch (Exception e) {
            
        }
    }

    // 2.a : Display the sender and recipient of all stored messages
    public static String displaySenderAndRecipient(String senderName) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i] != null) {
                sb.append("Sender: ").append(senderName)
                  .append(" | Recipient: ").append(storedMessages[i].getRecipient())
                  .append("\n");
            }
        }
        return sb.toString().trim();
    }

    // 2.b & Test 2 : Find the longest message (among sent and stored messages)
    public static String getLongestMessage() {
        String longest = "";
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i] != null && sentMessages[i].getMessageText().length() > longest.length()) {
                longest = sentMessages[i].getMessageText();
            }
        }
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i] != null && storedMessages[i].getMessageText().length() > longest.length()) {
                longest = storedMessages[i].getMessageText();
            }
        }
        return longest;
    }

    // 2.c & Test 3 : Search for a message using its message ID
    public static String searchByMessageID(String searchId) {
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i] != null && sentMessages[i].getMessageID().equals(searchId)) {
                return "Recipient: " + sentMessages[i].getRecipient() + "\nMessage: " + sentMessages[i].getMessageText();
            }
        }
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i] != null && storedMessages[i].getMessageID().equals(searchId)) {
                return "Recipient: " + storedMessages[i].getRecipient() + "\nMessage: " + storedMessages[i].getMessageText();
            }
        }
        return "Message ID not found.";
    }

    // 2.d & Test 4 : Search for all messages sent to or stored by a recipient
    public static String searchMessagesByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i] != null && sentMessages[i].getRecipient().equals(recipient)) {
                if (sb.length() > 0) sb.append(" ");
                sb.append(sentMessages[i].getMessageText());
            }
        }
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i] != null && storedMessages[i].getRecipient().equals(recipient)) {
                if (sb.length() > 0) sb.append(" ");
                sb.append(storedMessages[i].getMessageText());
            }
        }
        return sb.toString();
    }

    // 2.e & Test 5 : Delete a message using its message Hash
    public static String deleteMessageByHash(String hash) {
        // Search in StoredMessages
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i] != null && storedMessages[i].getMessageHash().equals(hash)) {
                String deletedText = storedMessages[i].getMessageText();
                for (int j = i; j < storedCount - 1; j++) {
                    storedMessages[j] = storedMessages[j + 1];
                }
                storedMessages[--storedCount] = null;
                removeHashFromGlobalArray(hash);
                saveMessagesToJson();
                return "Message: \"" + deletedText + "\" successfully deleted.";
            }
        }
        // Search in SentMessages
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i] != null && sentMessages[i].getMessageHash().equals(hash)) {
                String deletedText = sentMessages[i].getMessageText();
                for (int j = i; j < sentCount - 1; j++) {
                    sentMessages[j] = sentMessages[j + 1];
                }
                sentMessages[--sentCount] = null;
                removeHashFromGlobalArray(hash);
                return "Message: \"" + deletedText + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    private static void removeHashFromGlobalArray(String hash) {
        for (int i = 0; i < hashCount; i++) {
            if (messageHashes[i] != null && messageHashes[i].equals(hash)) {
                for (int j = i; j < hashCount - 1; j++) {
                    messageHashes[j] = messageHashes[j + 1];
                }
                messageHashes[--hashCount] = null;
                break;
            }
        }
    }

    // Test 6 : Report of sent messages (Message Hash, Recipient, Message)
    public static String displaySentReport() {
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i] != null) {
                report.append("Message Hash: ").append(sentMessages[i].getMessageHash()).append("\n")
                      .append("Recipient: ").append(sentMessages[i].getRecipient()).append("\n")
                      .append("Message: ").append(sentMessages[i].getMessageText()).append("\n\n");
            }
        }
        return report.toString().trim();
    }

    // 2.f : Complete report of all stored messages
    public static String displayStoredReport() {
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i] != null) {
                report.append(storedMessages[i].printMessages()).append("\n\n");
            }
        }
        return report.toString().trim();
    }
}