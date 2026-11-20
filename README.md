# PROG5121 Final PoE - QuickChat Application

## Project Overview
This repository contains the complete Java console implementation for **PROG5121 (Parts 1, 2, and 3)**. The application features regex-validated user registration and login, interactive QuickChat messaging with JSON persistence, and dynamic parallel-array message management and reporting.

---

## Key Features

### Part 1: Registration & Authentication
* **Username & Password Validation:** Enforces `_` and `<= 5` characters for usernames, and `>= 8` characters with uppercase, digit, and special character complexity rules for passwords[cite: 8].
* **Cell Phone Number Validation:** Uses regular expressions to validate South African international numbers (`+27` followed by 9 digits)[cite: 9].

### Part 2: QuickChat Messaging
* **10-Digit Message IDs:** Auto-generates and strictly validates 10-digit numeric IDs (`^\d{10}$`)[cite: 14, 29].
* **Message Hashing:** Generates uppercase hashes formatted as `[ID Prefix]:[Message Number]:[FIRSTWORDLASTWORD]`[cite: 14].
* **Delivery & Storage Actions:** Supports sending messages, disregarding/deleting message payloads, and persisting messages to `.json` files via `FileWriter`[cite: 15, 29].

### Part 3: Data Storage & Task Reports
* **Dynamic Parallel Arrays:** Populates `sentMessages`, `disregardedMessages`, `storedMessages`, `messageHashes`, and `messageIDs` without hardcoding[cite: 19].
* **JSON File Loader:** Reads stored `message_*.json` files from disk into the stored messages array using `BufferedReader`[cite: 19].
* **Search, Deletion & Reporting:** Displays sender/recipient pairs, finds the longest message, searches by Message ID or recipient cell number, deletes messages via Message Hash, and generates full sent-message reports[cite: 19].

---

## Repository Files
* `Login.java` — User registration and authentication validation[cite: 10]
* `Message.java` — Message properties, hash creation, and JSON persistence[cite: 16]
* `MessageManager.java` — Parallel array operations, JSON loading, and reporting[cite: 19]
* `Main.java` — Interactive console menus and input validation loops
* `LoginTest.java` & `MessageTest.java` — JUnit 4 unit test suites
* `.github/workflows/TestJava.yml` — Automated CI testing workflow[cite: 28]
