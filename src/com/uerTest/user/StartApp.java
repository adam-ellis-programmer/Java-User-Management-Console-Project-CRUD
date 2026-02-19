package com.uerTest.user;

public class StartApp {
    public static void start() {
        UserService userService = new UserService();
        UserController userController = new UserController(userService);
        userController.run();
    }
}