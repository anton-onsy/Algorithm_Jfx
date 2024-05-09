package com.example.algorithm_jfx;

public class Student {
    private String name;
    private int grade;

    // Constructor
    public Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public Student(int i) {
        this.grade = i;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for grade
    public int getGrade() {
        return grade;
    }

    // Setter for grade
    public void setGrade(int grade) {
        this.grade = grade;
    }
}
