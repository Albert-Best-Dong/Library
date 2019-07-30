package com.example.albert.library.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Person {
    private String name;
    private String sex;
    private int age;
    private Date borrowTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    @Override
    public String toString() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String time = s.format(borrowTime);
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", borrowTime=" + time +
                '}';
    }
}
