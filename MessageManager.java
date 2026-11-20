package prog5121_part1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MessageManager {
    // Required Part 3 Arrays
    private String[] sentMessages = new String[0];
    private String[] disregardedMessages = new String[0];
    private String[] storedMessages = new String[0];
    private String[] messageHashes = new String[0];
    private String[] messageIDs = new String[0];

    // Parallel arrays to track sender, recipient, full text, and status flag
    private String[] allMessages = new String[0];
    private String[] recipients = new String[0];
    private String[] senders = new String[0];
    private String[] flags = new String[0];

    // Dynamically adds an item to a standard array without hardcoding size
    private String[] appendToArray(String[] original, String newItem) {
        String[] updated = new String[original.length + 1];
        System.arraycopy(original, 0, updated, 0, original.length);
        updated[original.length] = newItem;
        return updated;
    }

    // Removes an element at a specific index from a parallel array
    private String[] removeFromArrayAt(String[] original, int index) {
        if (index < 0 || index >= original.length) return original;
        String[] updated = new String[original.length - 1];
        int pos = 0;
        for (int i = 0; i < original.length; i++) {
            if (i != index) {
                updated[pos++] = original[i];
            }
        }
        return updated;
    }

    // Removes the first matching string from a category array
    private String[] removeValueFromArray(String[] original, String value) {
        for (int i = 0; i < original.length; i++) {
            if (original[i] != null && original[i].equals(value)) {
                return removeFromArrayAt(original, i);
            }
        }
        return original;
    }

    // Populates all parallel arrays dynamically
    public void addMessageRecord(String sender, String recipient, String text, String id, String hash, String flag) {
        this.allMessages = appendToArray(this.allMessages, text);
        this.recipients = appendToArray(this.recipients, recipient);
        this.senders = appendToArray(this.senders, sender);
        this.messageIDs = appendToArray(this.messageIDs, id);
        this.messageHashes = appendToArray(this.messageHashes, hash);
        this.flags = appendToArray(this.flags, flag);

        if ("Sent".equalsIgnoreCase(flag)) {
            this.sentMessages = appendToArray(this.sentMessages, text);
        } else if ("Stored".equalsIgnoreCase(flag)) {
            this.storedMessages = appendToArray(this.storedMessages, text);
        } else if ("Disregard".equalsIgnoreCase(flag)) {
            this.disregardedMessages = appendToArray(this.disregardedMessages, text);
        }
    }

    // Reads stored JSON files from project directory into storedMessages array
    // Attribution: BufferedReader & File directory filtering adapted from Oracle Java Docs
    public String loadStoredMessagesFromJSON(String defaultSender) {
        File dir = new File(".");
        File[] jsonFiles = dir.listFiles((d, name) -> name.startsWith("message_") && name.endsWith(".json"));

        if (jsonFiles == null || jsonFiles.length == 0) {
            return "No stored JSON files found.";
        }

        int loaded = 0;
        for (File file : jsonFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line, id = "", recipient = "", text = "", hash = "";
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("\"messageID\":")) id = extractJsonValue(line);
                    else if (line.startsWith("\"recipient\":")) recipient = extractJsonValue(line);
                    else if (line.startsWith("\"messageText\":")) text = extractJsonValue(line);
                    else if (line.startsWith("\"messageHash\":")) hash = extractJsonValue(line);
                }
                if (!text.isEmpty() && !containsValue(this.messageIDs, id)) {
                    addMessageRecord(defaultSender, recipient, text, id, hash, "Stored");
                    loaded++;
                }
            } catch (IOException e) {
                return "Error reading JSON file: " + e.getMessage();
            }
        }
        return loaded > 0 ? loaded + " stored message(s) loaded from JSON." : "All JSON files already loaded.";
    }

    private String extractJsonValue(String line) {
        String val = line.substring(line.indexOf(":") + 1).trim();
        if (val.endsWith(",")) val = val.substring(0, val.length() - 1).trim();
        if (val.startsWith("\"") && val.endsWith("\"")) val = val.substring(1, val.length() - 1);
        return val.replace("\\\"", "\"");
    }

    private boolean containsValue(String[] arr, String target) {
        for (String s : arr) {
            if (s != null && s.equals(target)) return true;
        }
        return false;
    }

    // 2a. Display sender and recipient of all sent/stored messages
    public String displaySendersAndRecipients() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allMessages.length; i++) {
            if ("Sent".equalsIgnoreCase(flags[i]) || "Stored".equalsIgnoreCase(flags[i])) {
                sb.append("Sender: ").append(senders[i])
                  .append(" | Recipient: ").append(recipients[i])
                  .append(" (").append(flags[i]).append(")\n");
            }
        }
        return sb.length() > 0 ? sb.toString().trim() : "No messages found.";
    }

    // 2b. Display the longest message
    public String getLongestMessage() {
        String longest = "";
        for (String msg : allMessages) {
            if (msg != null && msg.length() > longest.length()) {
                longest = msg;
            }
        }
        return longest;
    }

    // 2c. Search by Message ID (Returns message text for unit testing)
    public String searchByMessageID(String searchID) {
        for (int i = 0; i < messageIDs.length; i++) {
            if (messageIDs[i] != null && messageIDs[i].equals(searchID)) {
                return allMessages[i];
            }
        }
        return "Message ID not found.";
    }

    // 2c (Menu Display). Search by Message ID and return Recipient + Message
    public String searchMessageDetailsByID(String searchID) {
        for (int i = 0; i < messageIDs.length; i++) {
            if (messageIDs[i] != null && messageIDs[i].equals(searchID)) {
                return "Recipient: " + recipients[i] + "\nMessage: " + allMessages[i];
            }
        }
        return "Message ID not found.";
    }

    // 2d. Search all messages sent or stored for a particular recipient
    public String[] searchByRecipient(String targetRecipient) {
        String[] matches = new String[0];
        for (int i = 0; i < recipients.length; i++) {
            if (recipients[i] != null && recipients[i].equals(targetRecipient)
                    && ("Sent".equalsIgnoreCase(flags[i]) || "Stored".equalsIgnoreCase(flags[i]))) {
                matches = appendToArray(matches, allMessages[i]);
            }
        }
        return matches;
    }

    // 2e. Delete a message using the message hash
    public String deleteMessageByHash(String targetHash) {
        for (int i = 0; i < messageHashes.length; i++) {
            if (messageHashes[i] != null && messageHashes[i].equalsIgnoreCase(targetHash)) {
                String deletedText = allMessages[i];
                String deletedFlag = flags[i];

                if ("Sent".equalsIgnoreCase(deletedFlag)) {
                    this.sentMessages = removeValueFromArray(this.sentMessages, deletedText);
                } else if ("Stored".equalsIgnoreCase(deletedFlag)) {
                    this.storedMessages = removeValueFromArray(this.storedMessages, deletedText);
                } else if ("Disregard".equalsIgnoreCase(deletedFlag)) {
                    this.disregardedMessages = removeValueFromArray(this.disregardedMessages, deletedText);
                }

                this.allMessages = removeFromArrayAt(this.allMessages, i);
                this.recipients = removeFromArrayAt(this.recipients, i);
                this.senders = removeFromArrayAt(this.senders, i);
                this.messageIDs = removeFromArrayAt(this.messageIDs, i);
                this.messageHashes = removeFromArrayAt(this.messageHashes, i);
                this.flags = removeFromArrayAt(this.flags, i);

                String cleanText = deletedText.endsWith(".") ? deletedText.substring(0, deletedText.length() - 1) : deletedText;
                return "Message \"" + cleanText + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // 2f. Display report listing all sent messages (Message Hash, Recipient, Message)
    public String displaySentMessagesReport() {
        StringBuilder report = new StringBuilder("=== SENT MESSAGES REPORT ===\n");
        boolean found = false;
        for (int i = 0; i < allMessages.length; i++) {
            if ("Sent".equalsIgnoreCase(flags[i])) {
                found = true;
                report.append("Message Hash: ").append(messageHashes[i]).append("\n")
                      .append("Recipient: ").append(recipients[i]).append("\n")
                      .append("Message: ").append(allMessages[i]).append("\n")
                      .append("----------------------------\n");
            }
        }
        return found ? report.toString().trim() : "No sent messages to display.";
    }

    // Getters for Unit Tests
    public String[] getSentMessages() { return sentMessages; }
    public String[] getDisregardedMessages() { return disregardedMessages; }
    public String[] getStoredMessages() { return storedMessages; }
    public String[] getMessageHashes() { return messageHashes; }
    public String[] getMessageIDs() { return messageIDs; }
}