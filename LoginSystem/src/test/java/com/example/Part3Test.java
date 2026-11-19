package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Part3Test {

    private Message m1, m2, m3, m4, m5;

    @BeforeEach
    void setUp() {
        MessageArrayManager.reset();

        m1 = new Message("+27834557896", "Did you get the cake?");
        m2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        m4 = new Message("0838884567", "It is dinner time !");
        m5 = new Message("+27838884567", "Ok, I am leaving without you.");

        MessageArrayManager.addSentMessage(m1);
        MessageArrayManager.addStoredMessage(m2);
        MessageArrayManager.addDisregardedMessage(m3);
        MessageArrayManager.addSentMessage(m4);
        MessageArrayManager.addStoredMessage(m5);
    }

    @Test
    void testSentMessagesArrayCorrectlyPopulated() {
        assertEquals(2, MessageArrayManager.sentCount);
        assertEquals("Did you get the cake?", MessageArrayManager.sentMessages[0].getMessageText());
        assertEquals("It is dinner time !", MessageArrayManager.sentMessages[1].getMessageText());
    }

    @Test
    void testDisplayLongestMessage() {
        String longest = MessageArrayManager.getLongestMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    void testSearchForMessageID() {
        String searchResult = MessageArrayManager.searchByMessageID(m4.getMessageID());
        assertTrue(searchResult.contains("0838884567"));
        assertTrue(searchResult.contains("It is dinner time !"));
    }

    @Test
    void testSearchMessagesByRecipient() {
        String result = MessageArrayManager.searchMessagesByRecipient("+27838884567");
        assertEquals(
            "Where are you? You are late! I have asked you to be on time. Ok, I am leaving without you.",
            result
        );
    }

    @Test
    void testDeleteMessageUsingHash() {
        String hashToDelete = m2.getMessageHash();
        String response = MessageArrayManager.deleteMessageByHash(hashToDelete);

        assertEquals(
            "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
            response
        );
        assertEquals(1, MessageArrayManager.storedCount);
    }

    @Test
    void testDisplayReport() {
        String report = MessageArrayManager.displaySentReport();

        assertTrue(report.contains(m1.getMessageHash()));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));

        assertTrue(report.contains(m4.getMessageHash()));
        assertTrue(report.contains("0838884567"));
        assertTrue(report.contains("It is dinner time !"));
    }
}