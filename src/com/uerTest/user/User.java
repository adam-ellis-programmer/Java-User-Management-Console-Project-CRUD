package com.uerTest.user;

public class User {
    private String id;  // changed from int to String
    private String name;
    private String email;
    private String role;
    private boolean active;
    private int age;

    // ===========
    // CONSTRUCTOR
    // ===========
    public User(String id, String name, String email, String role, boolean active, int age) {
        this.id = id;  // changed from int to String
        this.name = name;
        this.email = email;
        this.role = role;
        this.active = active;
        this.age = age;
    }

    // ===================
    // GETTERS AND SETTERS
    // ===================
    public String getId() {  // changed from int to String
        return id;
    }

    public void setId(String id) {  // changed from int to String
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", active=" + active +
                ", age=" + age +
                '}';
    }
}