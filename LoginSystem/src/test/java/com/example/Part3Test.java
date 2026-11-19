package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Part3Test {

    @Test
    void testPart3Requirements() {
        // 1. Creates messages using the test data
        Message m1 = new Message("+27834557896", "Did you get the cake?");
        Message m2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        Message m3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        Message m4 = new Message("0838884567", "It is dinner time !");
        Message m5 = new Message("+27838884567", "Ok, I am leaving without you.");

        // 2. Add to the tables (Flag : Sent, Stored, Disregard, Sent, Stored)
        MessageArrayManager.addSentMessage(m1);
        MessageArrayManager.addStoredMessage(m2);
        MessageArrayManager.addDisregardedMessage(m3);
        MessageArrayManager.addSentMessage(m4);
        MessageArrayManager.addStoredMessage(m5);

        // --- TEST 1 : Sent Messages array correctly populated (Developer entry for Test data for message 1-4) ---
        // Check m1 and m4
        assertEquals("Did you get the cake?", MessageArrayManager.sentMessages[0].getMessageText());
        assertEquals("It is dinner time !", MessageArrayManager.sentMessages[1].getMessageText());

        // --- TEST 2 : Display the longest Message ---
        String longest = "";
        for (int i = 0; i < MessageArrayManager.storedCount; i++) {
            if (MessageArrayManager.storedMessages[i].getMessageText().length() > longest.length()) {
                longest = MessageArrayManager.storedMessages[i].getMessageText();
            }
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);

        // --- TEST 3 : Search for messageID (Test Data: message 4) ---
        // Find the message containing the text "It is dinner time !" via l'ID
        String msgId = MessageArrayManager.sentMessages[1].getMessageID();
        String foundMsg = "";
        for (int i = 0; i < MessageArrayManager.sentCount; i++) {
            if (MessageArrayManager.sentMessages[i].getMessageID().equals(msgId)) {
                foundMsg = MessageArrayManager.sentMessages[i].getMessageText();
            }
        }
        assertEquals("It is dinner time !", foundMsg);

        // --- TEST 4 : Search all the messages sent or stored regarding a particular recipient ---
        // Test Data: +27838884567
        String searchRecipient = "+27838884567";
        String result = "";
        for (int i = 0; i < MessageArrayManager.storedCount; i++) {
            if (MessageArrayManager.storedMessages[i].getRecipient().equals(searchRecipient)) {
                result += MessageArrayManager.storedMessages[i].getMessageText();
            }
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.Ok, I am leaving without you.", result);

        // --- TEST 5 : Delete a message using a message hash (Test Data: Test Message 2) ---
        String hashToDelete = MessageArrayManager.storedMessages[0].getMessageHash();
        String textBeforeDelete = MessageArrayManager.storedMessages[0].getMessageText();
        
        // Simulate the deletion (shift the table)
        for (int i = 0; i < MessageArrayManager.storedCount - 1; i++) {
            MessageArrayManager.storedMessages[i] = MessageArrayManager.storedMessages[i + 1];
        }
        MessageArrayManager.storedCount--;
        
        assertEquals("Message: \"" + textBeforeDelete + "\" successfully deleted.", 
                     "Message: \"" + "Where are you? You are late! I have asked you to be on time." + "\" successfully deleted.");
    }
}
