package com.example.proguard.test;

public class User {
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User() {
        throw new RuntimeException("test exception");
    }

    public static void main(String[] args) {
        User user = new User();
        user.setId("q2");
        user.setName("sdf");
    }
}
