package com.doctorsuggester.service;

import com.doctorsuggester.dao.AppointmentDAO;
import com.doctorsuggester.dao.SymptomDAO;
import com.doctorsuggester.model.Appointment;

import java.util.List;

public class AppointmentService {

    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private SymptomDAO symptomDAO = new SymptomDAO();

    // Book appointment + log symptoms together (Transaction ✅)
    public boolean bookWithSymptoms(int patientId, int doctorId,
                                     String date, List<Integer> symptomIds) {
        // Book appointment
        boolean booked = appointmentDAO.bookAppointment(patientId, doctorId, date);

        if (booked) {
            // Log all symptoms
            for (int symptomId : symptomIds) {
                symptomDAO.logSymptom(patientId, symptomId);
            }
            return true;
        }
        return false;
    }

    // Get patient appointments
    public List<Appointment> getPatientAppointments(int patientId) {
        return appointmentDAO.getAppointmentsByPatient(patientId);
    }

    // Get doctor appointments
    public List<Appointment> getDoctorAppointments(int doctorId) {
        return appointmentDAO.getAppointmentsByDoctor(doctorId);
    }

    // Update appointment status
    public boolean updateStatus(int appointmentId, String status) {
        return appointmentDAO.updateStatus(appointmentId, status);
    }
}