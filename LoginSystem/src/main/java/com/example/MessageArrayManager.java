package com.example;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MessageArrayManager {
    
    // Tables requested by the POE
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

    // Add Sent Message
    public static void addSentMessage(Message m) {
        sentMessages[sentCount++] = m;
        messageHashes[hashCount++] = m.getMessageHash();
        messageIDs[idCount++] = m.getMessageID();
    }

    // Add disregarded Message
    public static void addDisregardedMessage(Message m) {
        disregardedMessages[disregardedCount++] = m;
        messageHashes[hashCount++] = m.getMessageHash();
        messageIDs[idCount++] = m.getMessageID();
    }

    // Add stored message
    public static void addStoredMessage(Message m) {
        storedMessages[storedCount++] = m;
        messageHashes[hashCount++] = m.getMessageHash();
        messageIDs[idCount++] = m.getMessageID();
        saveMessagesToJson(); 
    }

    // JSON File
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

    // POE
    public static void loadMessagesFromJson() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("messages.json")));
            JSONArray arr = new JSONArray(content);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String recipient = obj.getString("Recipient");
                String text = obj.getString("Message");
                Message m = new Message(recipient, text);
                addStoredMessage(m);
            }
        } catch (Exception e) {
            
        }
    }
}
