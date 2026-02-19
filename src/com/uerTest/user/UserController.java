package com.uerTest.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// shift methods to different files
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
            // keeps displaying until we exit loop
            printMenu();

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    addUser();
                    break; // breaks out of the SWITCH ONLY
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
                    // The only way to stop the while loop is to set running = false:
                    running = false;
                    break; // breaks out of the SWITCH ONLY
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
        System.out.println("  5. Find User By Email"); // add this line
        System.out.println("  0. Exit");
        System.out.println("══════════════════════════");
        System.out.print("  Select an option: ");
    }

    // Think of it like emergency exits in a building — you need one at every point
    // along the corridor, not just at the start and end.

    // We also need it inside the helper methods like `getValidEmail` because those
    // have their **own loops** that would keep running and never see the exit check
    // in `addUser`:

    // So the rule is — any method with its own loop needs its own exit check, and
    // any prompt that reads input needs a check after it.

    private boolean isExit(String input) {
        return input.equalsIgnoreCase("exit");
    }

    // ===========
    // ADD USER
    // ===========
    // next line/??
    private void addUser() {
        boolean test = true;

        System.out.println("\n── Add New User ──");

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
        String ageInput = getValidAgeOrBlank(); // returns String not int
        if (isExit(ageInput))
            return;

        // then convert to int later when creating the user
        int age = Integer.parseInt(ageInput);
 
        System.out.print("Is user active? (yes / no): ");
        boolean active = scanner.nextLine().equalsIgnoreCase("yes");

        // auto generate UUID
        String id = java.util.UUID.randomUUID().toString();

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

    // The key thing to understand is that removeUser() is just a regular method
    // call — when it finishes executing, control automatically returns to wherever
    // it was called from, which is the run() method inside the while loop.
    private void removeUser() {
        System.out.println("\n── Remove User ──");

        // show all users first so they can see the ids
        viewAllUsers();

        System.out.print("Enter user ID to remove: ");
        String id = scanner.nextLine();

        User user = userService.getUserById(id);

        if (user != null) {
            System.out.print("Are you sure you want to remove " + user.getName() + "? (yes / no): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                userService.removeUserById(id);
                System.out.println("\n✓ User " + user.getName() + " has been removed successfully!");
            } else {
                System.out.println("Remove cancelled.");
            }
        } else {
            System.out.println("No user found with that ID.");
        }
        // The method ending IS the signal to return control.
        // function ends here — control returns to caller automatically
    }

    // ===========
    // UPDATE USER
    // ===========

    // So the HashMap only ever contains the fields the user actually typed
    // something for. If they left everything blank the map would be empty and
    // nothing would change.

    // This is exactly how a PATCH request works in a REST API — you only send the
    // fields you want to change, the rest stay untouched. As opposed to a PUT
    // request where you send the entire object and replace everything.

    // So without even knowing it you've implemented the PATCH pattern!

    private void updateUser() {
        System.out.println("\n── Update User ──");

        // show all users first so they can see the ids
        viewAllUsers();

        System.out.print("Enter user ID to update: ");
        String id = scanner.nextLine();

        User user = userService.getUserById(id);

        if (user != null) {
            System.out.println("\nLeave a field blank to keep the current value.");

            System.out.print("Enter new name (" + user.getName() + "): ");
            String name = scanner.nextLine();

            System.out.print("Enter new email (" + user.getEmail() + "): ");
            String email = scanner.nextLine();

            System.out.print("Enter new role (" + user.getRole() + ") (ADMIN / USER): ");
            String role = scanner.nextLine().toUpperCase();

            System.out.print("Enter new age (" + user.getAge() + "): ");
            String ageInput = getValidAgeOrBlank();

            System.out.print("Is user active? (" + user.isActive() + ") (yes / no): ");
            String activeInput = scanner.nextLine();

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

            if (!ageInput.isEmpty())
                updates.put("age", Integer.parseInt(ageInput));

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
        System.out.println("\n── Find User By Email ──");

        List<String> sysEmails = userService.getAllEmails();

        System.out.println("Emails to search:");
        System.out.println("===================");
        sysEmails.forEach((e) -> System.out.println("---> " + e + ""));

        System.out.print("Enter email to search: ");
        String email = getValidEmail();

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

    private String getValidAgeOrBlank() {
        while (true) {
            String input = scanner.nextLine();

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

    // try/catch approach won't work for email validation because
    // reading a string never throws an exception — any text is a valid string.

    private String getValidEmail() {
        String email = "";
        boolean valid = false;

        while (!valid) {
            email = scanner.nextLine();

            if (email.contains("@") && email.contains(".")) {
                valid = true;
            } else {
                System.out.print("Invalid email, please try again: ");
            }
        }

        return email;
    }

}
