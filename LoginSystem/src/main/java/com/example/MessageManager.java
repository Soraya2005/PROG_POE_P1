package com.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class MessageManager {

    private ArrayList<Message> sentMessages = new ArrayList<>();
    private int totalMessages = 0;

    // Adds a message to the list
    public void addMessage(Message m) {
        totalMessages++;
        m.setNumMessagesSent(totalMessages);
        sentMessages.add(m);
    }

    // Returns total number of messages sent
    public int returnTotalMessages() {
        return totalMessages;
    }

    // Prints all sent messages
    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append(m.printMessages()).append("\n---\n");
        }
        return sb.toString();
    }

    // Stores messages in a JSON file
    public void storeMessage() {
        JSONArray arr = new JSONArray();
        for (Message m : sentMessages) {
            JSONObject obj = new JSONObject();
            obj.put("MessageID", m.getMessageID());
            obj.put("MessageHash", m.getMessageHash());
            obj.put("Recipient", m.getRecipient());
            obj.put("Message", m.getMessageText());
            arr.put(obj);
        }
        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(arr.toString(4));
            file.flush();
        } catch (IOException e) {
            System.out.println("Error saving messages: " + e.getMessage());
        }
    }
}