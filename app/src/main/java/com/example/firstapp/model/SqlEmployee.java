package com.example.firstapp.model;

public class SqlEmployee {
    private int id;
    private int salary;
    private int age;
    private String name;
    private String address;
    private String email;
    private String phone;
    private  String gender;

    public SqlEmployee(int id, int salary, int age, String name, String address, String email, String phone, String gender) {
        this.id = id;
        this.salary = salary;
        this.age = age;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public int getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }
}
