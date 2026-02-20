package com.uerTest.user;

public class StartApp {
    public static void start() {
        UserService userService = new UserService();
        UserController userController = new UserController(userService);
        userController.run();
    }
}

/**
 * UserController now holds a reference to the same UserService instance. This
 * is important — we're not creating a second UserService, we're passing the
 * existing one in so they both share the same data.
 */