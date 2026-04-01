package com.doctorsuggester.service;

public class ReminderService implements Runnable {

    private String patientName;
    private String appointmentDate;
    private volatile boolean running = true;

    public ReminderService(String patientName, String appointmentDate) {
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
    }

    @Override
    public void run() {
        while (running) {
            try {
                System.out.println("🔔 Reminder: " + patientName +
                                   ", your appointment is on " + appointmentDate);
                Thread.sleep(60000); // Remind every 60 seconds
            } catch (InterruptedException e) {
                System.out.println("Reminder stopped.");
                running = false;
            }
        }
    }

    public void stop() {
        running = false;
    }

    // Start reminder as background thread
    public static void startReminder(String patientName, String date) {
        ReminderService reminder = new ReminderService(patientName, date);
        Thread thread = new Thread(reminder);
        thread.setDaemon(true); // Stops when app closes
        thread.start();
    }
}