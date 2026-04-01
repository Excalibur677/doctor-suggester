package com.doctorsuggester.model;

public class Doctor extends User {
    private int doctorId;
    private String specialization;
    private int experience;
    private String availableDays;

    public Doctor(String name, String email, String password, String phone,
                  String specialization, int experience, String availableDays) {
        super(name, email, password, "doctor", phone);
        this.specialization = specialization;
        this.experience = experience;
        this.availableDays = availableDays;
    }

    @Override
    public String getDashboardTitle() {
        return "Doctor Dashboard - Dr. " + getName();
    }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public int getExperience() { return experience; }
    public String getAvailableDays() { return availableDays; }
    public void setUserId(int userId) { super.setUserId(userId); }
    
}