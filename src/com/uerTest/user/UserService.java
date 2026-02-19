package com.uerTest.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {

    private List<User> users;

    // Constructor
    public UserService() {
        users = new ArrayList<>(List.of(
                new User("1", "Alice", "alice@example.com", "ADMIN", true, 30),
                new User("2", "Bob", "bob@example.com", "USER", true, 25),
                new User("3", "Charlie", "charlie@example.com", "USER", false, 35),
                new User("4", "Diana", "diana@example.com", "ADMIN", true, 28),
                new User("5", "Eve", "eve@example.com", "USER", false, 22)));
    }

    // ===========
    // GET ALL
    // ===========
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // ===========
    // FILTER
    // ===========
    public List<User> getActiveUsers() {
        return users.stream()
                .filter(User::isActive)
                .collect(Collectors.toList());
    }

    public List<User> getUsersByRole(String role) {
        return users.stream()
                .filter(u -> u.getRole().equals(role))
                .collect(Collectors.toList());
    }

    // changed from int to String
    public User getUserById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id)) // changed == to .equals()
                .findFirst()
                .orElse(null);
    }

    public User getUserEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    // ===========
    // MAP
    // ===========
    public List<String> getAllNames() {
        return users.stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }

    public List<String> getAllEmails() {
        return users.stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

    // ===========
    // REDUCE
    // ===========
    public int getTotalAge() {
        return users.stream()
                .map(User::getAge)
                .reduce(0, Integer::sum);
    }

    public double getAverageAge() {
        return users.stream()
                .mapToInt(User::getAge)
                .average()
                .orElse(0);
    }

    // ===========
    // GROUP BY
    // ===========
    public Map<String, List<User>> getUsersGroupedByRole() {
        return users.stream()
                .collect(Collectors.groupingBy(User::getRole));
    }

    // ===========
    // SORT
    // ===========
    public List<User> getUsersSortedByName() {
        return users.stream()
                .sorted((a, b) -> a.getName().compareTo(b.getName()))
                .collect(Collectors.toList());
    }

    public List<User> getUsersSortedByAge() {
        return users.stream()
                .sorted((a, b) -> Integer.compare(a.getAge(), b.getAge()))
                .collect(Collectors.toList());
    }

    // ===========
    // ADD / REMOVE
    // ===========
    public void addUser(User user) {
        users.add(user);
    }

    // changed from int to String
    public void removeUserById(String id) {
        users.removeIf(u -> u.getId().equals(id));
    }

    public void removeAllUsers() {
        users.clear();
    }

    public void removeNonAdminUsers() {
        users.removeIf(u -> !u.getRole().equals("ADMIN"));
    }

    // ===========
    // UPDATE
    // ===========
    public void updateUserById(String id, Map<String, Object> updates) {
        // like a map update in javaScript ...
        users = users.stream()
                .map(user -> {
                    if (user.getId().equals(id)) {
                        if (updates.containsKey("name"))
                            user.setName((String) updates.get("name"));

                        if (updates.containsKey("email"))
                            user.setEmail((String) updates.get("email"));

                        if (updates.containsKey("role"))
                            user.setRole((String) updates.get("role"));

                        if (updates.containsKey("age"))
                            user.setAge((Integer) updates.get("age"));

                        if (updates.containsKey("active"))
                            user.setActive((Boolean) updates.get("active"));

                    }
                    return user;
                })
                .collect(Collectors.toList());
    }
}