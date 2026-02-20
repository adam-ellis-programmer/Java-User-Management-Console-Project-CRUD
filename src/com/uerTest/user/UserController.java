package com.uerTest.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserController {

    private UserService userService;
    private Scanner scanner;

    // Constructor — UserService gets passed in
    public UserController(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    // ===========
    // MENU
    // ===========
    public void run() {
        boolean running = true;

        while (running) {
            printMenu();

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    addUser();
                    break;
                case "2":
                    viewAllUsers();
                    break;
                case "3":
                    removeUser();
                    break;
                case "4":
                    updateUser();
                    break;
                case "5":
                    findUserByEmail();
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
        scanner.close();
    }

    // ===========
    // PRINT MENU
    // ===========
    private void printMenu() {
        System.out.println("\n══════════════════════════");
        System.out.println("   Welcome to User Manager");
        System.out.println("══════════════════════════");
        System.out.println("  1. Add User");
        System.out.println("  2. View All Users");
        System.out.println("  3. Remove User");
        System.out.println("  4. Update User");
        System.out.println("  5. Find User By Email");
        System.out.println("  0. Exit");
        System.out.println("══════════════════════════");
        System.out.println("  (type 'exit' at any time to return to menu)");
        System.out.print("  Select an option: ");
    }

    // ===========
    // EXIT CHECK
    // ===========

    // equalsIgnoreCase("exit") compares the input against the string "exit" and
    // returns true or false. So the whole method just returns that boolean result
    // directly.
    private boolean isExit(String input) {
        return input.equalsIgnoreCase("exit");
    }

    // ===========
    // ADD USER
    // ===========
    private void addUser() {
        System.out.println("\n── Add New User ── (type 'exit' to return to menu)");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        if (isExit(name))
            return;

        System.out.print("Enter email: ");
        String email = getValidEmail();
        if (isExit(email))
            return;

        System.out.print("Enter role (ADMIN / USER): ");
        String role = scanner.nextLine().toUpperCase();
        if (isExit(role))
            return;

        System.out.print("Enter age: ");
        String ageInput = getValidAgeOrBlank();
        if (isExit(ageInput))
            return;

        System.out.print("Is user active? (yes / no): ");
        String activeInput = scanner.nextLine();
        if (isExit(activeInput))
            return;

        // only reaches here if no exit was typed
        String id = java.util.UUID.randomUUID().toString();
        int age = Integer.parseInt(ageInput);
        boolean active = activeInput.equalsIgnoreCase("yes");

        User newUser = new User(id, name, email, role, active, age);
        userService.addUser(newUser);

        System.out.println("\n✓ User " + name + " has been added successfully!");
        System.out.println(newUser);
    }

    // ===========
    // VIEW ALL USERS
    // ===========
    private void viewAllUsers() {
        List<User> users = userService.getAllUsers();

        System.out.println("\n── All Users ──");

        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("Total users: " + users.size() + "\n");
            users.forEach(user -> System.out.println(
                    "  ID:     " + user.getId() + "\n" +
                            "  Name:   " + user.getName() + "\n" +
                            "  Email:  " + user.getEmail() + "\n" +
                            "  Role:   " + user.getRole() + "\n" +
                            "  Active: " + user.isActive() + "\n" +
                            "  Age:    " + user.getAge() + "\n" +
                            "  ──────────────────────"));
        }
    }

    // ===========
    // REMOVE USER
    // ===========
    private void removeUser() {
        System.out.println("\n── Remove User ── (type 'exit' to return to menu)");

        viewAllUsers();

        System.out.print("Enter user ID to remove: ");
        String id = scanner.nextLine();
        if (isExit(id))
            return;

        User user = userService.getUserById(id);

        if (user != null) {
            System.out.print("Are you sure you want to remove " + user.getName() + "? (yes / no): ");
            String confirm = scanner.nextLine();
            if (isExit(confirm))
                return;

            if (confirm.equalsIgnoreCase("yes")) {
                userService.removeUserById(id);
                System.out.println("\n✓ User " + user.getName() + " has been removed successfully!");
            } else {
                System.out.println("Remove cancelled.");
            }
        } else {
            System.out.println("No user found with that ID.");
        }
    }

    // ===========
    // UPDATE USER
    // ===========
    private void updateUser() {
        System.out.println("\n── Update User ── (type 'exit' to return to menu)");

        viewAllUsers();

        System.out.print("Enter user ID to update: ");
        String id = scanner.nextLine();
        if (isExit(id))
            return;

        User user = userService.getUserById(id);

        if (user != null) {
            System.out.println("\nLeave a field blank to keep the current value.");

            System.out.print("Enter new name (" + user.getName() + "): ");
            String name = scanner.nextLine();
            if (isExit(name))
                return;

            System.out.print("Enter new email (" + user.getEmail() + "): ");
            String email = scanner.nextLine();
            if (isExit(email))
                return;

            System.out.print("Enter new role (" + user.getRole() + ") (ADMIN / USER): ");
            String role = scanner.nextLine().toUpperCase();
            if (isExit(role))
                return;

            System.out.print("Enter new age (" + user.getAge() + "): ");
            String ageInput = getValidAgeOrBlank();
            if (isExit(ageInput))
                return;

            System.out.print("Is user active? (" + user.isActive() + ") (yes / no): ");
            String activeInput = scanner.nextLine();
            if (isExit(activeInput))
                return;

            // build the HashMap — only add fields that were not left blank
            Map<String, Object> updates = new HashMap<>();

            if (!name.isEmpty())
                updates.put("name", name);
            if (!email.isEmpty())
                updates.put("email", email);
            if (!role.isEmpty())
                updates.put("role", role);
            if (!ageInput.isEmpty())
                updates.put("age", Integer.parseInt(ageInput));
            if (!activeInput.isEmpty())
                updates.put("active", activeInput.equalsIgnoreCase("yes"));

            userService.updateUserById(id, updates);

            System.out.println("\n✓ User " + user.getName() + " has been updated successfully!");
            System.out.println(userService.getUserById(id));

        } else {
            System.out.println("No user found with that ID.");
        }
    }

    // ====================
    // FIND USER BY EMAIL
    // ====================
    private void findUserByEmail() {
        System.out.println("\n── Find User By Email ── (type 'exit' to return to menu)");

        List<String> sysEmails = userService.getAllEmails();
        System.out.println("Emails to search:");
        System.out.println("===================");
        sysEmails.forEach((e) -> System.out.println("---> " + e));

        System.out.print("Enter email to search: ");
        String email = getValidEmail();
        if (isExit(email))
            return;

        User user = userService.getUserEmail(email);

        if (user != null) {
            System.out.println("\n✓ User found!");
            System.out.println(
                    "  ID:     " + user.getId() + "\n" +
                            "  Name:   " + user.getName() + "\n" +
                            "  Email:  " + user.getEmail() + "\n" +
                            "  Role:   " + user.getRole() + "\n" +
                            "  Active: " + user.isActive() + "\n" +
                            "  Age:    " + user.getAge());
        } else {
            System.out.println("No user found with email: " + email);
        }
    }

    // ===============
    // HELPERS
    // ===============

    // The loop can end — but only through a return statement, not through
    // the loop condition itself.

    private String getValidAgeOrBlank() {
        // while(true) — ends only via return or break:
        while (true) {
            String input = scanner.nextLine();

            // returns true IF INPUT IS "exit"
            if (isExit(input))
                return input; // pass exit through

            if (input.isEmpty())
                return "";

            try {
                Integer.parseInt(input);
                return input;
            } catch (NumberFormatException e) {
                System.out.print("Invalid age, please enter a number or leave blank: ");
            }
        }
    }

    private int getValidAge() {
        int age = 0;
        boolean valid = false;

        while (!valid) {
            try {
                age = Integer.parseInt(scanner.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.print("Invalid age, please enter a number: ");
            }
        }
        return age;
    }

    private String getValidEmail() {
        String email = "";
        boolean valid = false;

        while (!valid) {
            email = scanner.nextLine();

            if (isExit(email))
                return email; // pass exit through

            if (email.contains("@") && email.contains(".")) {
                valid = true;
            } else {
                System.out.print("Invalid email, please try again: ");
            }
        }
        return email;
    }
}