package models;

import annotations.NotNull;
import annotations.Range;

public class Employee extends User {

    @NotNull
    private static String company = null;

    @Range(min = 0, max = 1_000_000)
    private double salary;

    public Employee(String name, String email, Integer age, double salary) {
        super(name, email, age);
        this.salary = salary;
    }
}