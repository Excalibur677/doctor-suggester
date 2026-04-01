package com.doctorsuggester.model;

public class Patient extends User {

    public Patient(String name, String email, String password, String phone) {
        super(name, email, password, "patient", phone);
    }

    @Override
    public String getDashboardTitle() {
        return "Patient Dashboard - " + getName();
    }
}