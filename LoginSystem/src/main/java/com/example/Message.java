package com.example;

import java.util.Random;

public class Message {

    // Attributes
    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String message;
    private String messageHash;

    // Constructor
    public Message(String recipient, String message) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }

    // Generates a random 10-digit message ID
    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    // Checks the message ID is not more than 10 characters
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Checks the recipient cell number
    public String checkRecipientCell() {
        // CORRECTION : La regex accepte un "+" suivi de 1 à 12 chiffres.
        // Cela valide le numéro de test "+27718693002" et rejette "08575975889" (qui n'a pas de "+").
        if (recipient.matches("^\\+[0-9]{1,12}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Creates the Message Hash
    public String createMessageHash() {
        if (message == null || message.isEmpty()) {
            return "";
        }
        String firstTwo = messageID.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        return firstTwo + ":" + numMessagesSent + ":" + firstWord + lastWord;
    }

    // Returns the message ID
    public String getMessageID() {
        return messageID;
    }

    // Returns the recipient
    public String getRecipient() {
        return recipient;
    }

    // Returns the message text
    public String getMessage() {
        return message;
    }

    // Returns the message hash
    public String getMessageHash() {
        return messageHash;
    }

    // Returns a formatted string with all message details
    public String printMessages() {
        return "Message ID: " + messageID + "\n"
             + "Message Hash: " + messageHash + "\n"
             + "Recipient: " + recipient + "\n"
             + "Message: " + message;
    }

    // Sets the number of messages sent (used by MessageManager)
    public void setNumMessagesSent(int num) {
        this.numMessagesSent = num;
    }

    // Returns the stored message (for JSON)
    public String getMessageText() {
        return message;
    }
}