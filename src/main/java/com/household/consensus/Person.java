package com.household.consensus;

public class Person {
    String fName;
    String lName;
    String address;
    byte age;

    public Person(String fName, String lName, String address, byte age) {
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.age = age;
    }

    public String getFName() {
        return fName;
    }

    public String getLName() {
        return lName;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}
