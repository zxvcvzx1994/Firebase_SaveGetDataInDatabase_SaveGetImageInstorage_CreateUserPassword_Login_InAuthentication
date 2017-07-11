package com.example.sd.myapplication.Module;

/**
 * Created by kh on 7/9/2017.
 */

public class Student {
    private String name;
    private String address;
    private int age;

    public Student(String name, String address, int age){
        this.name = name;
        this.address =  address;
        this.age = age;

    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
