package com.doctorsuggester.service;

import com.doctorsuggester.dao.SymptomDAO;
import com.doctorsuggester.util.InvalidSymptomException;
import com.doctorsuggester.util.DoctorNotFoundException;

import java.util.*;

public class SymptomAnalyzer {

    private SymptomDAO symptomDAO = new SymptomDAO();

    // Core logic - finds best matching specialization ✅
    public String analyze(List<String> selectedSymptoms) 
                          throws InvalidSymptomException, DoctorNotFoundException {

        if (selectedSymptoms == null || selectedSymptoms.isEmpty()) {
            throw new InvalidSymptomException("No symptoms selected!");
        }

        // HashMap to score each specialization
        HashMap<String, Integer> scoreMap = new HashMap<>();

        for (String symptom : selectedSymptoms) {
            String spec = symptomDAO.getSpecialization(symptom);
            if (spec != null) {
                scoreMap.put(spec, scoreMap.getOrDefault(spec, 0) + 1);
            }
        }

        if (scoreMap.isEmpty()) {
            throw new DoctorNotFoundException("No matching doctor found for given symptoms!");
        }

        // Lambda expression - sort by score ✅
        String bestMatch = scoreMap.entrySet()
            .stream()
            .sorted((a, b) -> b.getValue() - a.getValue())
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(null);

        return bestMatch;
    }

    // Get all suggestions if tie
    public List<String> analyzeAll(List<String> selectedSymptoms) 
                                    throws InvalidSymptomException {

        if (selectedSymptoms == null || selectedSymptoms.isEmpty()) {
            throw new InvalidSymptomException("No symptoms selected!");
        }

        HashMap<String, Integer> scoreMap = new HashMap<>();

        for (String symptom : selectedSymptoms) {
            String spec = symptomDAO.getSpecialization(symptom);
            if (spec != null) {
                scoreMap.put(spec, scoreMap.getOrDefault(spec, 0) + 1);
            }
        }

        // Sort by score descending using lambda ✅
        List<String> result = new ArrayList<>();
        scoreMap.entrySet()
            .stream()
            .sorted((a, b) -> b.getValue() - a.getValue())
            .forEach(e -> result.add(e.getKey() + " (" + e.getValue() + " symptom match)"));

        return result;
    }
}