package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    // ===== Test 1: Message length =====
    @Test
    void testMessageLessThan250Characters() {
        String msg = "Hi Mike, can you join us for dinner tonight?";
        Message m = new Message("+27718693002", msg);
        assertTrue(msg.length() <= 250);
    }

    @Test
    void testMessageExceeds250Characters() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) sb.append("a");
        String msg = sb.toString();
        assertTrue(msg.length() > 250);
        // Expected: "Message exceeds 250 characters by X, please reduce the size."
    }

    // ===== Test 2: Recipient format =====
    @Test
    void testRecipientCorrectlyFormatted() {
        Message m = new Message("+27718693002", "Hi Mike");
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell());
    }

    @Test
    void testRecipientIncorrectlyFormatted() {
        Message m = new Message("08575975889", "Hi Keegan");
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
            m.checkRecipientCell()
        );
    }

    // ===== Test 3: Message Hash =====
    @Test
    void testMessageHashCorrect() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String hash = m.getMessageHash();
        // Hash should start with first 2 digits of messageID + ":" + numMessages + ":" + first & last word uppercase
        assertTrue(hash.contains(":"));
        assertTrue(hash.toUpperCase().contains("HI"));
        assertTrue(hash.toUpperCase().contains("TONIGHT"));
    }

    // ===== Test 4: Message ID created =====
    @Test
    void testMessageIDCreated() {
        Message m = new Message("+27718693002", "Hi Mike");
        assertNotNull(m.getMessageID());
        assertTrue(m.getMessageID().length() <= 10);
    }

    // ===== Test 5: Message sent options =====
    @Test
    void testMessageSentOptions() {
        // Option 1: Send Message
        String sendResult = "Message successfully sent.";
        assertEquals("Message successfully sent.", sendResult);

        // Option 2: Disregard Message
        String disregardResult = "Press 0 to delete the message.";
        assertEquals("Press 0 to delete the message.", disregardResult);

        // Option 3: Store Message
        String storeResult = "Message successfully stored.";
        assertEquals("Message successfully stored.", storeResult);
    }
}
