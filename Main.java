package prog5121_part1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login userLogin = new Login();

        System.out.println("=== USER REGISTRATION ===");

        // First Name Input
        System.out.print("Enter First Name: ");
        String firstName = input.nextLine();

        // Last Name Input
        System.out.print("Enter Last Name: ");
        String lastName = input.nextLine();

        // Username Validation Loop
        System.out.print("Enter Username (must contain '_' and be <= 5 chars): ");
        String username = input.nextLine();
        while (!userLogin.checkUserName(username)) {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            System.out.print("Please try again: ");
            username = input.nextLine();
        }
        System.out.println("Username successfully captured.");

        // Password Validation Loop
        System.out.print("Enter Password (>= 8 chars, 1 uppercase, 1 digit, 1 special char): ");
        String password = input.nextLine();
        while (!userLogin.checkPasswordComplexity(password)) {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            System.out.print("Please try again: ");
            password = input.nextLine();
        }
        System.out.println("Password successfully captured.");

        // Cell Phone Number Validation Loop 
        System.out.print("Enter Cell Phone Number (e.g. +27838968976): ");
        String cellNumber = input.nextLine();
        while (!userLogin.checkCellPhoneNumber(cellNumber)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            System.out.print("Please correct the number and try again: ");
            cellNumber = input.nextLine();
        }
        System.out.println("Cell phone number successfully added.");

        // Complete Registration
        userLogin.registerUser(username, password, firstName, lastName, cellNumber);

        // Login System Loop
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

        input.close();
    }
}