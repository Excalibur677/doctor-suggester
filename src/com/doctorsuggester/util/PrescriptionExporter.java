package com.doctorsuggester.util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrescriptionExporter {

    // Export prescription to .txt file using FileOutputStream ✅
    public static boolean exportPrescription(String patientName,
                                              String doctorName,
                                              String date,
                                              String medicine,
                                              String notes) {
        String fileName = "Prescription_" + patientName.replace(" ", "_")
                          + "_" + date + ".txt";
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            BufferedWriter writer = new BufferedWriter(
                                    new OutputStreamWriter(fos));

            writer.write("========================================");
            writer.newLine();
            writer.write("       🏥 DOCTOR SUGGESTER SYSTEM       ");
            writer.newLine();
            writer.write("           PRESCRIPTION SLIP            ");
            writer.newLine();
            writer.write("========================================");
            writer.newLine();
            writer.write("Date     : " + date);
            writer.newLine();
            writer.write("Patient  : " + patientName);
            writer.newLine();
            writer.write("Doctor   : Dr. " + doctorName);
            writer.newLine();
            writer.write("----------------------------------------");
            writer.newLine();
            writer.write("Medicine : " + medicine);
            writer.newLine();
            writer.write("Notes    : " + notes);
            writer.newLine();
            writer.write("----------------------------------------");
            writer.newLine();
            writer.write("Generated: " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            writer.newLine();
            writer.write("========================================");

            writer.close();
            fos.close();

            return true;
        } catch (IOException e) {
            System.out.println("Export Error: " + e.getMessage());
            return false;
        }
    }

    // Read back the prescription file ✅
    public static String readPrescription(String filePath) {
        StringBuilder content = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            fis.close();
        } catch (IOException e) {
            System.out.println("Read Error: " + e.getMessage());
        }
        return content.toString();
    }
}