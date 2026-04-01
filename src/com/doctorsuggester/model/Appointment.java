package com.doctorsuggester.model;

public class Appointment {

    // Enum covers syllabus Unit I - Enumerated types ✅
    public enum Status {
        PENDING, CONFIRMED, COMPLETED, CANCELLED
    }

    private int appointmentId;
    private int patientId;
    private int doctorId;
    private String appointmentDate;
    private Status status;
    private String patientName;
    private String doctorName;

    public Appointment(int patientId, int doctorId, String appointmentDate) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.status = Status.PENDING;
    }

    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int id) { this.appointmentId = id; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public String getAppointmentDate() { return appointmentDate; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String name) { this.patientName = name; }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String name) { this.doctorName = name; }
}