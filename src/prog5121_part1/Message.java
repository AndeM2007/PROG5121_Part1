package prog5121_part1;

import java.util.Random;
import java.util.regex.Pattern;

public class Message {
    private String messageID;
    private static int numMessagesSent = 0; // Accumulated messages counter
    private String recipientCell;
    private String messagePayload;
    private String messageHash;
    private String deliveryStatus;
    private int messageNumber;

    // Default Constructor
    public Message() {
    }

    // Overloaded Constructor
    public Message(String recipientCell, String messagePayload, int messageNumber) {
        this.messageID = generateMessageID();
        this.recipientCell = recipientCell;
        this.messagePayload = messagePayload;
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash();
    }

    // Generates a random 10-digit number for Message ID
    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    // Rubric requirement: Checks that message ID is not more than 10 characters
    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    // Rubric requirement: Ensures recipient cell starts with international code (+27) and valid length
    public boolean checkRecipientCell(String cellNumber) {
        String phoneRegex = "^\\+27[0-9]{9}$";
        return Pattern.matches(phoneRegex, cellNumber);
    }

    // Rubric requirement: Checks message payload length (<= 250 characters)
    public String checkMessageLength(String message) {
        if (message.length() > 250) {
            return "Please enter a message of less than 250 characters.";
        }
        return "Message sent";
    }

    // Rubric requirement: Format -> [First 2 numbers of ID]:[Message Number]:[FIRST & LAST WORD IN CAPS]
    public String createMessageHash() {
        if (this.messageID == null || this.messageID.length() < 2 || this.messagePayload == null || this.messagePayload.trim().isEmpty()) {
            return "00:0:INVALID";
        }

        String firstTwoID = this.messageID.substring(0, 2);
        
        String[] words = this.messagePayload.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String combinedWords = (firstWord + lastWord).replaceAll("[^a-zA-Z0-9]", "").toUpperCase();

        return firstTwoID + ":" + this.messageNumber + ":" + combinedWords;
    }

    // Rubric requirement: Handles Send, Disregard, or Store choices
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

    // Rubric requirement: Prints full message details in specified order: ID, Hash, Recipient, Message
    public String printMessages() {
        return "Message ID: " + this.messageID + "\n" +
               "Message Hash: " + this.messageHash + "\n" +
               "Recipient: " + this.recipientCell + "\n" +
               "Message: " + this.messagePayload;
    }

    // Rubric requirement: Returns total accumulated sent messages
    public int returnTotalMessagess() {
        return numMessagesSent;
    }

    // Rubric requirement: Researched JSON format output method
    public void storeMessageJSON() {
        String jsonPayload = "{\n" +
                "  \"messageID\": \"" + this.messageID + "\",\n" +
                "  \"recipientCell\": \"" + this.recipientCell + "\",\n" +
                "  \"messagePayload\": \"" + this.messagePayload + "\",\n" +
                "  \"messageHash\": \"" + this.messageHash + "\"\n" +
                "}";
        System.out.println("JSON Format Output:\n" + jsonPayload);
    }

    // Getters
    public String getMessageID() { return messageID; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessagePayload() { return messagePayload; }
    public String getMessageHash() { return messageHash; }
}
