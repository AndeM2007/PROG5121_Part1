package prog5121_part1;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void testRecipientCellValid() {
        Message msg = new Message();
        assertTrue(msg.checkRecipientCell("+27718693002"));
    }

    @Test
    public void testRecipientCellInvalid() {
        Message msg = new Message();
        assertFalse(msg.checkRecipientCell("0718693002"));
    }

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message();
        String result = msg.checkMessageLength("Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message sent", result);
    }

    @Test
    public void testMessageLengthExceeded() {
        Message msg = new Message();
        String longText = "A".repeat(251);
        String result = msg.checkMessageLength(longText);
        assertEquals("Please enter a message of less than 250 characters.", result);
    }

    @Test
    public void testSentMessageOptionSend() {
        Message msg = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String status = msg.SentMessage(1);
        assertEquals("Message successfully sent", status);
    }

    @Test
    public void testSentMessageOptionStore() {
        Message msg = new Message("+27718693002", "Test Store");
        String status = msg.SentMessage(3);
        assertEquals("Message successfully stored", status);
    }

    @Test
    public void testSentMessageOptionDisregard() {
        Message msg = new Message("+27718693002", "Test Disregard");
        String status = msg.SentMessage(2);
        assertEquals("Press 0 to delete the message", status);
    }
}
