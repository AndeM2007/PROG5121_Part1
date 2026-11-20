package prog5121_part1;

import java.util.Scanner;
/**
 * Main QuickChat application integrating User Registration/Login, Messaging, and Part 3 Reports.
 */
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login userLogin = new Login();
        MessageManager manager = new MessageManager();

        System.out.println("=== USER REGISTRATION ===");
        System.out.print("Enter First Name: ");
        String firstName = input.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = input.nextLine();

        System.out.print("Enter Username: ");
        String username = input.nextLine();
        while (!userLogin.checkUserName(username)) {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            System.out.print("Please try again: ");
            username = input.nextLine();
        }

        System.out.print("Enter Password: ");
        String password = input.nextLine();
        while (!userLogin.checkPasswordComplexity(password)) {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            System.out.print("Please try again: ");
            password = input.nextLine();
        }

        // Cell Phone Number Validation Loop 
        System.out.print("Enter Cell Phone Number (e.g. +27838968976): ");
        String cellNumber = input.nextLine();
        while (!userLogin.checkCellPhoneNumber(cellNumber)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            System.out.print("Please correct the number and try again: ");
            cellNumber = input.nextLine();
        }
        System.out.println("Cell phone number successfully added.");

        userLogin.registerUser(username, password, firstName, lastName, cellNumber);

        System.out.println("\n=== USER LOGIN ===");
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Enter Username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter Password: ");
            String loginPassword = input.nextLine();

            loggedIn = userLogin.loginUser(loginUsername, loginPassword);
            System.out.println(userLogin.returnLoginStatus(loggedIn));
        }

        String currentSender = firstName + " " + lastName;
        int sessionCounter = 0;

        // --- QUICKCHAT MAIN MENU ---
        System.out.println("\nWelcome to QuickChat.");
        boolean running = true;

        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages & Reports");
            System.out.print("Choice (1-4): ");

            if (!input.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                input.nextLine();
                continue;
            }

            int option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    System.out.print("How many messages would you like to send? ");
                    while (!input.hasNextInt()) {
                        System.out.print("Please enter a valid number: ");
                        input.nextLine();
                    }
                    int count = input.nextInt();
                    input.nextLine();

                    Message lastMsg = null;

                    for (int i = 0; i < count; i++) {
                        System.out.println("\n--- Message " + (i + 1) + " of " + count + " ---");
                        Message validator = new Message();

                        System.out.print("Enter Recipient Cell Number (+27...): ");
                        String recipient = input.nextLine();
                        while (!validator.checkRecipientCell(recipient).equals("Cell phone number successfully captured.")) {
                            System.out.println(validator.checkRecipientCell(recipient));
                            System.out.print("Please re-enter Recipient Cell Number: ");
                            recipient = input.nextLine();
                        }

                        System.out.print("Enter Message (max 250 chars): ");
                        String text = input.nextLine();
                        while (!validator.checkMessageLength(text).equals("Message ready to send.")) {
                            System.out.println(validator.checkMessageLength(text));
                            System.out.print("Please re-enter Message: ");
                            text = input.nextLine();
                        }

                        Message msg = new Message(sessionCounter++, recipient, text);
                        String capturedID = msg.getMessageID();
                        String capturedHash = msg.getMessageHash();

                        System.out.println("\nChoose an action:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Choice (1-3): ");

                        while (!input.hasNextInt()) {
                            System.out.print("Please enter 1, 2, or 3: ");
                            input.nextLine();
                        }
                        int action = input.nextInt();
                        input.nextLine();

                        String status = msg.SentMessage(action);
                        System.out.println(status);

                        if (action == 1) {
                            manager.addMessageRecord(currentSender, recipient, text, capturedID, capturedHash, "Sent");
                        } else if (action == 2) {
                            manager.addMessageRecord(currentSender, recipient, text, capturedID, capturedHash, "Disregard");
                        } else if (action == 3) {
                            manager.addMessageRecord(currentSender, recipient, text, capturedID, capturedHash, "Stored");
                        }

                        System.out.println("\n" + msg.printMessages());
                        lastMsg = msg;
                    }

                    if (lastMsg != null) {
                        System.out.println("\nTotal Messages Sent: " + lastMsg.returnTotalMessagess());
                    }
                    break;

                case 2:
                    System.out.println("\n" + manager.displaySentMessagesReport());
                    break;

                case 3:
                    System.out.println("Exiting QuickChat. Goodbye!");
                    running = false;
                    break;

                case 4:
                    handleReportsSubMenu(input, manager, currentSender);
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1-4.");
            }
        }
        input.close();
    }

    private static void handleReportsSubMenu(Scanner input, MessageManager manager, String sender) {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== STORED MESSAGES & REPORTS ===");
            System.out.println("a) Display sender and recipient of all messages");
            System.out.println("b) Display the longest message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search all messages for a particular recipient");
            System.out.println("e) Delete a message using a message hash");
            System.out.println("f) Display full report of all sent messages");
            System.out.println("g) Load stored JSON files into array");
            System.out.println("x) Return to Main Menu");
            System.out.print("Select option: ");
            String choice = input.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a":
                    System.out.println("\n" + manager.displaySendersAndRecipients());
                    break;
                case "b":
                    String longest = manager.getLongestMessage();
                    System.out.println("\nLongest Message: " + (longest.isEmpty() ? "None" : longest));
                    break;
                case "c":
                    System.out.print("Enter Message ID: ");
                    System.out.println("\n" + manager.searchMessageDetailsByID(input.nextLine().trim()));
                    break;
                case "d":
                    System.out.print("Enter Recipient Cell (+27...): ");
                    String[] matches = manager.searchByRecipient(input.nextLine().trim());
                    if (matches.length == 0) {
                        System.out.println("No messages found for that recipient.");
                    } else {
                        for (String m : matches) System.out.println("- " + m);
                    }
                    break;
                case "e":
                    System.out.print("Enter Message Hash to delete: ");
                    System.out.println("\n" + manager.deleteMessageByHash(input.nextLine().trim()));
                    break;
                case "f":
                    System.out.println("\n" + manager.displaySentMessagesReport());
                    break;
                case "g":
                    System.out.println("\n" + manager.loadStoredMessagesFromJSON(sender));
                    break;
                case "x":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid sub-option. Try again.");
            }
        }
    }
}
