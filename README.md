# PROG5121 Part 1 - Login & Registration Application
# PROG5121 Part 1 - User Registration and Login Application

## Overview
This Java console application implements a user registration and login system for **PROG5121 Part 1**. It validates user credentials including usernames, complex passwords, and South African cell phone numbers using interactive `Scanner` loops and Object-Oriented Programming (OOP) principles.

---

## Features

* **Username Validation:** Ensures the username contains an underscore (`_`) and does not exceed 5 characters in length.
* **Password Complexity Checking:** Guarantees passwords contain at least 8 characters, a capital letter, a digit, and a special character.
* **Cell Phone Number Validation:** Confirms the cell phone number is formatted correctly with the international code (`+27`).
* **Console User Interface:** Operates strictly via standard input/output (`Scanner` and `System.out`) with `while` loops that re-prompt users upon invalid input.
* **Automated Testing:** Unit test suite created with JUnit to verify all edge cases for login and registration functionality.

---

## Project Structure

```text
prog5121_part1/
├── src/
│   └── prog5121_part1/
│       ├── Login.java      # Contains logic for user validation and authentication
│       └── Main.java       # Interactive console app with Scanner input loops
└── test/
    └── prog5121_part1/
        └── LoginTest.java  # JUnit unit tests for Login class
