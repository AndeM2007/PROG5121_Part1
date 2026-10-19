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

        System.out.print("Enter Cell Phone Number (e.g. +27838968976): ");
        String cellNumber = input.nextLine();
        while (!userLogin.checkCellPhoneNumber(cellNumber)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            System.out.print("Please correct the number and try again: ");
            cellNumber = input.nextLine();
        }

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

        // --- PART 2: MAIN MENU APPLICATION LOOP ---
        if (loggedIn) {
            System.out.println("\nWelcome to EasyKanban");
            
            boolean running = true;
            while (running) {
                System.out.println("\nSelect an option:");
                System.out.println("1) Option 1: Add tasks");
                System.out.println("2) Option 2: Show report");
                System.out.println("3) Option 3: Quit");
                System.out.print("Choice: ");

                if (input.hasNextInt()) {
                    int choice = input.nextInt();
                    input.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            System.out.println("Task feature coming soon.");
                            break;
                        case 2:
                            System.out.println("Show report feature coming soon.");
                            break;
                        case 3:
                            System.out.println("Exiting application. Goodbye!");
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid option. Please enter a number between 1 and 3.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number (1-3).");
                    input.nextLine(); // Clear invalid input
                }
            }
        }

        input.close();
    }
}
