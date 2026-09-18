package prog5121_part1;

public class Login {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellNumber;

    // Validate Username: Must contain '_' and be 5 characters or fewer
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Validate Password: Minimum 8 characters, at least 1 capital letter, 1 number, and 1 special character
    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasCapital = !password.equals(password.toLowerCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[^a-zA-Z0-9].*");
        
        return hasCapital && hasDigit && hasSpecial;
    }

    // Validate Cell Phone Number: Must contain international code (e.g. +27) and valid digits
    // Validates cell phone format
    public boolean checkCellPhoneNumber(String cellNumber) {
        return cellNumber.startsWith("+27") && cellNumber.length() == 12;
    }

    // Process Registration
    public String registerUser(String username, String password, String firstName, String lastName, String cellNumber) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell number is incorrectly formatted or does not contain international code; please correct the number and try again.";
        }

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellNumber = cellNumber;

        return "User registered successfully.";
    }

    // Validate Login Credentials
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername.equals(this.username) && enteredPassword.equals(this.password);
    }

    // Return Login Outcome Message
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + " " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
