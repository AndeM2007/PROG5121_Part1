package prog5121_part1;

import java.util.Random;
import java.util.regex.Pattern;

public class Message {
    private String messageID;
    private static int numMessagesSent = 0; // Tracks total count across messages
    private String recipientCell;
    private String messagePayload;
    private String messageHash;
    private String deliveryStatus;

    // Default Constructor
    public Message() {
    }

    // Overloaded Constructor
    public Message(String recipientCell, String messagePayload) {
        this.messageID = generateMessageID();
        this.recipientCell = recipientCell;
        this.messagePayload = messagePayload;
        this.messageHash = createMessageHash();
    }

    // Generate a random 10-digit Message ID
    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    // Ensure Message ID is not more than 10 characters
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    // Ensure recipient cell number is valid (+27 prefix and correct length)
    // Attribution: E.164 international phone format regex
    public boolean checkRecipientCell(String cellNumber) {
        String phoneRegex = "^\\+27[0-9]{9}$";
        return Pattern.matches(phoneRegex, cellNumber);
    }

    // Validate message payload length (<= 250 chars)
    public String checkMessageLength(String message) {
        if (message.length() > 250) {
            return "Please enter a message of less than 250 characters.";
        }
        return "Message sent";
    }

    // Create Message Hash: [First 2 digits ID]:[numMessagesSent]:[FIRST_WORD LAST_WORD] in ALL CAPS
    public String createMessageHash() {
        if (this.messageID == null || this.messageID.length() < 2 || this.messagePayload == null || this.messagePayload.trim().isEmpty()) {
            return "00:0:INVALID";
        }

        String firstTwoID = this.messageID.substring(0, 2);
        
        // Extract first and last word
        String[] words = this.messagePayload.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String combinedWords = (firstWord + lastWord).replaceAll("[^a-zA-Z0-9]", "").toUpperCase();

        return firstTwoID + ":" + numMessagesSent + ":" + combinedWords;
    }

    // Handle user action choice: Send, Disregard, or Store
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                numMessagesSent++;
                this.deliveryStatus = "Message successfully sent";
                return this.deliveryStatus;
            case 2:
                this.deliveryStatus = "Press 0 to delete the message";
                return this.deliveryStatus;
            case 3:
                this.deliveryStatus = "Message successfully stored";
                return this.deliveryStatus;
            default:
                return "Invalid selection.";
        }
    }

    // Return full formatted details of the message
    public String printMessages() {
        return "Message ID: " + this.messageID + "\n" +
               "Message Hash: " + this.messageHash + "\n" +
               "Recipient: " + this.recipientCell + "\n" +
               "Message: " + this.messagePayload;
    }

    // Return the total count of accumulated sent messages
    public int returnTotalMessagess() {
        return numMessagesSent;
    }

    // JSON Storage implementation (Required research method signature)
    public void storeMessageJSON() {
        // Formats the message data into a JSON structure
        String jsonPayload = "{\n" +
                "  \"messageID\": \"" + this.messageID + "\",\n" +
                "  \"recipientCell\": \"" + this.recipientCell + "\",\n" +
                "  \"messagePayload\": \"" + this.messagePayload + "\",\n" +
                "  \"messageHash\": \"" + this.messageHash + "\"\n" +
                "}";
        System.out.println("JSON Format Output:\n" + jsonPayload);
    }

    // Getters and Setters
    public String getMessageID() { return messageID; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessagePayload() { return messagePayload; }
    public String getMessageHash() { return messageHash; }
}
