// package com.uerTest.user;

// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// public class StartApp {
//     private static boolean found;

//     public static void start() {
//         System.out.println("App starting ...");

//         UserService userService = new UserService();

//         // ADD NEW USER:
//         User newUser = new User("200", "Adam", "adam@gmail.com", "CEO", true, 43);
//         userService.addUser(newUser);

//         List<User> allUsers = userService.getAllUsers();

//         User userByEmail = userService.getUserEmail("eve@example.com");

//         if (userByEmail != null) {
//             System.out.println("Found user: " + userByEmail.getName().toUpperCase());
//             found = true;
//         } else {
//             System.out.println("User not found");
//         }

//         if (found == true) {
//             System.out.println("FOUND HAS BEEN SET TO TRUE!");
//         } else if (found == false) {
//             System.out.println("FOUND HAS BEEN SET TO FALSE!");
//         }

//         var test = found == true ? "this is true" : "this is not ";
//         System.out.println(test);

//         // UPDATE — using HashMap to only update specific fields
//         // We use key / value pairs so use hash
//         Map<String, Object> changes = new HashMap<>();

//         changes.put("name", "Alice Updated");
//         changes.put("email", "aliceupdated@gmail.com");

//         userService.updateUserById("1", changes);

//         User updated = userService.getUserById("1");
//         System.out.println("UPDATED USER: " + updated);
//     }
// }