package com.doctorsuggester.service;

import com.doctorsuggester.util.InvalidSymptomException;
import com.doctorsuggester.util.DoctorNotFoundException;
import java.util.Arrays;
import java.util.List;

public class TestService {
    public static void main(String[] args) {
        SymptomAnalyzer analyzer = new SymptomAnalyzer();

        List<String> symptoms = Arrays.asList("Fever", "Cough", "Cold");

        try {
            String suggestion = analyzer.analyze(symptoms);
            System.out.println("✅ Suggested Specialization: " + suggestion);

            List<String> allSuggestions = analyzer.analyzeAll(symptoms);
            System.out.println("📋 All Suggestions: " + allSuggestions);

        } catch (InvalidSymptomException | DoctorNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}