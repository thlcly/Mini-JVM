package com.aaront.exercise.jvm;

public class EmployeeV1 {
    private String name;
    private int age;
    private long year;

    public EmployeeV1(String name, int age, long year) {
        this.name = name;
        this.age = age;
        this.year = year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void sayHello() {
        System.out.println("Hello , this is class Employee");
    }

    public static void main(String[] args) {
        EmployeeV1 p = new EmployeeV1("Andy", 29, 2017);
        p.sayHello();
    }
}