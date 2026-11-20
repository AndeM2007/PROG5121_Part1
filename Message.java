package prog5121_part1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
/**
 * Manages individual message properties, 10-digit ID validation, hashing, and JSON file persistence.
 */
public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private static int totalSentMessages = 0;

    public Message() {
    }

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Generates a random 10-digit numeric Message ID
    public String generateMessageID() {
        Random random = new Random();
        long id = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    // Fixed: Ensures Message ID is strictly 10 numeric digits
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.matches("^\\d{10}$");
    }

    // Validates cell phone number (+27 followed by 9 digits)
    // Attribution: Java Pattern regex syntax (Oracle Java Documentation)
    public String checkRecipientCell(String cellNumber) {
        String regex = "^\\+27\\d{9}$";
        if (cellNumber != null && cellNumber.matches(regex)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Fixed: Exact rubric return strings for message length check
    public String checkMessageLength(String text) {
        if (text == null || text.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = text.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    // Creates Message Hash: [First 2 digits of ID]:[messageNumber]:[FIRSTWORDLASTWORD]
    public String createMessageHash() {
        if (this.messageID == null || this.messageID.length() < 2 || this.messageText == null || this.messageText.trim().isEmpty()) {
            return "";
        }
        String firstTwoDigits = this.messageID.substring(0, 2);
        String[] words = this.messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        
        return (firstTwoDigits + ":" + this.messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    // Fixed: Handles Send, Disregard (actual deletion), and Store
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalSentMessages++;
                return "Message successfully sent.";
            case 2:
                this.messageText = null;
                this.messageHash = null;
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid selection.";
        }
    }

    // Displays details in required sequence: Message ID, Message Hash, Recipient, Message
    public String printMessages() {
        return "Message ID: " + this.messageID + "\n"
             + "Message Hash: " + (this.messageHash != null ? this.messageHash : "[DELETED]") + "\n"
             + "Recipient: " + this.recipient + "\n"
             + "Message: " + (this.messageText != null ? this.messageText : "[DELETED]");
    }

    public int returnTotalMessagess() {
        return totalSentMessages;
    }

    // Fixed: Persists message to an actual JSON file on disk
    // Attribution: FileWriter usage adapted from Oracle Java I/O Documentation
    public void storeMessage() {
        String safeText = this.messageText != null ? this.messageText.replace("\"", "\\\"") : "";
        String jsonContent = "{\n"
                + "  \"messageID\": \"" + this.messageID + "\",\n"
                + "  \"messageNumber\": " + this.messageNumber + ",\n"
                + "  \"recipient\": \"" + this.recipient + "\",\n"
                + "  \"messageText\": \"" + safeText + "\",\n"
                + "  \"messageHash\": \"" + (this.messageHash != null ? this.messageHash : "") + "\"\n"
                + "}";
        try (FileWriter writer = new FileWriter("message_" + this.messageID + ".json")) {
            writer.write(jsonContent);
        } catch (IOException e) {
            System.out.println("Error saving JSON file: " + e.getMessage());
        }
    }

    // Getters and Setters
    public String getMessageID() { return messageID; }
    public void setMessageID(String messageID) { this.messageID = messageID; }
    public int getMessageNumber() { return messageNumber; }
    public void setMessageNumber(int messageNumber) { this.messageNumber = messageNumber; }
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    public String getMessageHash() { return messageHash; }
    public void setMessageHash(String messageHash) { this.messageHash = messageHash; }
}
