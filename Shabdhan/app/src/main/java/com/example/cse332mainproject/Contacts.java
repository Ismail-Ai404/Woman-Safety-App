package com.example.cse332mainproject;

public class Contacts {

    private int id;
    private String name;
    private String number;
    private boolean isActive;

    public Contacts(int id, String name, String number, boolean isActive) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.isActive = isActive;
    }

    public Contacts() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Name = " + name + ", Number = +880" + number + " ";
    }
}
