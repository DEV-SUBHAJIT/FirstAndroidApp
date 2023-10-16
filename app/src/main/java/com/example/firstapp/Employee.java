package com.example.firstapp;

public class Employee {
    private String name, email, phone, age, address;

    public Employee(String name, String email, String phone, String age, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }
}
