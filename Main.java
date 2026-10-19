package prog5121_part1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login userLogin = new Login();

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

        // --- PART 2: QUICKCHAT APPLICATION LOOP ---
        if (loggedIn) {
            System.out.println("\nWelcome to QuickChat.");
            
            boolean running = true;
            while (running) {
                System.out.println("\nSelect an option:");
                System.out.println("Option 1) Send Messages");
                System.out.println("Option 2) Show recently sent messages");
                System.out.println("Option 3) Quit");
                System.out.print("Choice: ");

                if (input.hasNextInt()) {
                    int choice = input.nextInt();
                    input.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            System.out.print("How many messages would you like to enter? ");
                            int numMessages = input.nextInt();
                            input.nextLine(); // Consume newline

                            Message lastMessageObj = null;

                            // Rubric: For loop used to enter set number of messages
                            for (int i = 0; i < numMessages; i++) {
                                System.out.println("\n--- Entering Message " + (i + 1) + " of " + numMessages + " ---");
                                
                                System.out.print("Enter Recipient Cell Number (+27...): ");
                                String recipient = input.nextLine();

                                System.out.print("Enter Message Text: ");
                                String messageText = input.nextLine();

                                Message msg = new Message(recipient, messageText, i);

                                // Check message length
                                String lengthCheck = msg.checkMessageLength(messageText);
                                System.out.println(lengthCheck);

                                if (lengthCheck.equals("Message sent")) {
                                    System.out.println("\nSelect Send Option:");
                                    System.out.println("1) Send Message");
                                    System.out.println("2) Disregard Message");
                                    System.out.println("3) Store Message to send later");
                                    System.out.print("Choice: ");
                                    int sendChoice = input.nextInt();
                                    input.nextLine(); // Consume newline

                                    String actionStatus = msg.SentMessage(sendChoice);
                                    System.out.println(actionStatus);

                                    if (sendChoice == 1) {
                                        System.out.println("\n--- Message Details ---");
                                        System.out.println(msg.printMessages());
                                    }
                                }
                                lastMessageObj = msg;
                            }

                            if (lastMessageObj != null) {
                                System.out.println("\nTotal Messages Sent: " + lastMessageObj.returnTotalMessagess());
                            }
                            break;

                        case 2:
                            System.out.println("Coming Soon.");
                            break;

                        case 3:
                            System.out.println("Exiting QuickChat. Goodbye!");
                            running = false;
                            break;

                        default:
                            System.out.println("Invalid option. Please enter 1, 2, or 3.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number (1-3).");
                    input.nextLine();
                }
            }
        }

        input.close();
    }
}
