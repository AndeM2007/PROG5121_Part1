package prog5121_part1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * JUnit 4 test suite covering Part 2 messaging logic and Part 3 array reporting operations.
 */
public class MessageTest {
    private MessageManager manager;

    @Before
    public void setUp() {
        manager = new MessageManager();

        // Test Data Messages 1 - 5 from PROG5121 Rubric
        Message m1 = new Message(1, "+27834557896", "Did you get the cake?");
        manager.addMessageRecord("Developer", "+27834557896", "Did you get the cake?", m1.getMessageID(), m1.createMessageHash(), "Sent");

        Message m2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m2.setMessageID("1122334455");
        manager.addMessageRecord("Developer", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "1122334455", m2.createMessageHash(), "Stored");

        Message m3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.");
        manager.addMessageRecord("Developer", "+27834484567", "Yohoooo, I am at your gate.", m3.getMessageID(), m3.createMessageHash(), "Disregard");

        Message m4 = new Message(4, "+27838884567", "It is dinner time!");
        m4.setMessageID("0838884567");
        manager.addMessageRecord("0838884567", "+27838884567", "It is dinner time!", "0838884567", m4.createMessageHash(), "Sent");

        Message m5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
        manager.addMessageRecord("Developer", "+27838884567", "Ok, I am leaving without you.", m5.getMessageID(), m5.createMessageHash(), "Stored");
    }

    // --- PART 2 UNIT TESTS ---
    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message();
        assertEquals("Message ready to send.", msg.checkMessageLength("Hi Mike, can you join us for dinner tonight?"));
    }

    @Test
    public void testMessageLengthFailure() {
        Message msg = new Message();
        String longText = "A".repeat(260);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", msg.checkMessageLength(longText));
    }

    @Test
    public void testRecipientCellSuccess() {
        Message msg = new Message();
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell("+27718693002"));
    }

    @Test
    public void testRecipientCellFailure() {
        Message msg = new Message();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg.checkRecipientCell("08575975889"));
    }

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        msg.setMessageID("0012345678");
        assertEquals("00:0:HITONIGHT", msg.createMessageHash());
    }

    @Test
    public void testSentMessageOptions() {
        Message msg = new Message(1, "+27718693002", "Test message");
        assertEquals("Message successfully sent.", msg.SentMessage(1));
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
        assertNull(msg.getMessageText());
        assertEquals("Message successfully stored.", msg.SentMessage(3));
    }

    @Test
    public void testCheckMessageID() {
        Message msg = new Message(0, "+27718693002", "Hello");
        assertTrue(msg.checkMessageID());
        msg.setMessageID("12345");
        assertFalse(msg.checkMessageID());
    }

    // --- PART 3 UNIT TESTS ---
    @Test
    public void testSentMessagesArrayPopulated() {
        String[] expected = {"Did you get the cake?", "It is dinner time!"};
        assertArrayEquals(expected, manager.getSentMessages());
    }

    @Test
    public void testGetLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.", manager.getLongestMessage());
    }

    @Test
    public void testSearchByMessageID() {
        assertEquals("It is dinner time!", manager.searchByMessageID("0838884567"));
    }

    @Test
    public void testSearchByRecipient() {
        String[] results = manager.searchByRecipient("+27838884567");
        assertEquals(3, results.length);
        assertEquals("Where are you? You are late! I have asked you to be on time.", results[0]);
        assertEquals("It is dinner time!", results[1]);
        assertEquals("Ok, I am leaving without you.", results[2]);
    }

    @Test
    public void testDeleteMessageByHash() {
        // Message 2 hash -> 11:2:WHERETIME
        String response = manager.deleteMessageByHash("11:2:WHERETIME");
        assertEquals("Message \"Where are you? You are late! I have asked you to be on time\" successfully deleted.", response);
    }

    @Test
    public void testDisplayReport() {
        String report = manager.displaySentMessagesReport();
        assertTrue(report.contains("Message Hash:"));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));
        assertTrue(report.contains("Message: It is dinner time!"));
    }
}
