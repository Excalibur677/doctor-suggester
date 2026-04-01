package com.doctorsuggester.model;

public class Symptom {
    private int symptomId;
    private String symptomName;
    private String specialization;

    public Symptom(int symptomId, String symptomName, String specialization) {
        this.symptomId = symptomId;
        this.symptomName = symptomName;
        this.specialization = specialization;
    }

    public int getSymptomId() { return symptomId; }
    public String getSymptomName() { return symptomName; }
    public String getSpecialization() { return specialization; }
}