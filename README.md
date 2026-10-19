# PROG5121 PoE - User Registration, Authentication, and Messaging Application

## Project Overview
This repository contains the complete Java implementation for **PROG5121** (Part 1 and Part 2). The application provides a user registration system, credential authentication with strict input checks, and an interactive messaging platform operating via standard console input (`Scanner`) and `while` validation loops.

---

## Features

### Part 1: User Registration & Authentication
* **Username Validation:** Ensures usernames contain an underscore (`_`) and do not exceed 5 characters.
* **Password Complexity Checking:** Validates passwords to ensure a minimum length of 8 characters, at least one uppercase letter, one numeric digit, and one special character.
* **Cell Phone Number Validation:** Uses Regular Expressions (Regex) to ensure cell phone numbers start with the international code `+27` and follow the 12-character format.
* **Login Authentication:** Verifies entered credentials against stored user registration details and outputs custom welcome or failure messages.

### Part 2: Interactive Application Menu & Messaging System
* **Console Menu Navigation:** EasyKanban menu options (1: Add tasks/messages, 2: Show report, 3: Quit) running inside a controlled `while` loop.
* **Automated Message ID Generation:** Dynamically generates 10-digit unique message IDs.
* **Message Length Validation:** Restricts payload messages to 250 characters or fewer.
* **Message Hash Creation:** Generates a custom formatted message hash `[First 2 digits of ID]:[Message Count]:[FIRST_WORD LAST_WORD]`.
* **Delivery & Storage Actions:** Offers actions to Send, Disregard (delete), or Store messages locally.
* **JSON Output Formatting:** Method signature (`storeMessageJSON`) demonstrating JSON structured data output.

---

## Project Folder Structure

```text
PROG5121_Part1/
├── src/
│   └── prog5121_part1/
│       ├── Login.java      # User registration & credential validation logic
│       ├── Message.java    # Message processing, hash generation, and options
│       └── Main.java       # Interactive console interface and main menu loop
└── test/
    └── prog5121_part1/
        ├── LoginTest.java   # Unit tests for username, password, cell, and login status
        └── MessageTest.java # Unit tests for message validation, hash, and status options
