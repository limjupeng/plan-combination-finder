package com.telepathylabs.plancombinationfinder.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Plan {

    public String PlanName;
    public int Price;
    public String[] Features;

    public Plan(String name, int price, String[] features) {
        PlanName = name;
        Price = price;
        Features = features;
    }
    public static Plan getCheapestComboOfPlans(String allPlans,String featuresNeeded) {
        String[] lines = allPlans.split("\\r?\\n");
        List<Plan> plans = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            String name = parts[0];
            int price = Integer.parseInt(parts[1]);
            String[] features = new String[parts.length - 2];
            System.arraycopy(parts, 2, features, 0, parts.length - 2);
            plans.add(new Plan(name, price, features));
        }
        String[] desiredFeatures = featuresNeeded.split(",");

        return getCheapestComboOfPlans(plans, 0, desiredFeatures, 0, "");
    }
    private static Plan getCheapestComboOfPlans(List<Plan> plans, int startIndex, String[] remainingDesiredFeatures, int currentCost, String currentCombi) {
        if (startIndex >= plans.size()) return null;
        //store a copy of the remaining features, cost, and combi
        String[] updatedRF = remainingDesiredFeatures.clone();
        int totalCost = currentCost;
        StringBuilder totalCombi = new StringBuilder(currentCombi);
        boolean containsDesiredFeature = false;
        //for each feature in this plan, if the feature is a desired feature, check off from the remaining features
        for (int i = 0; i < updatedRF.length; i++) {
            if (updatedRF[i] == null) continue;
            if (Arrays.asList(plans.get(startIndex).Features).contains(updatedRF[i])) {
                updatedRF[i] = null;
                containsDesiredFeature = true;
            }
        }
        //if this plan has any desired feature, add to the total cost and combination
        if (containsDesiredFeature) {
            totalCost += plans.get(startIndex).Price;
            totalCombi.append(",").append(plans.get(startIndex).PlanName);
        }
        //if all features found, backtrack and compare if a combination with the next plan is cheaper than with this plan
        if (Arrays.stream(updatedRF).allMatch(Objects::isNull)) {
            Plan alt = getCheapestComboOfPlans(plans, startIndex + 1, remainingDesiredFeatures, currentCost, currentCombi);
            return (alt != null && alt.Price < totalCost) ? alt : new Plan(totalCombi.toString(), totalCost, updatedRF);
        } else { // check the next plan for the remaining desired features and use this plan as part of the combination
            Plan p = getCheapestComboOfPlans(plans, startIndex + 1, updatedRF, totalCost, totalCombi.toString());
            Plan alt = getCheapestComboOfPlans(plans, startIndex + 1, remainingDesiredFeatures, currentCost, currentCombi);
            if (p == null && alt == null) return null;
            if (p == null) return alt;
            if (alt == null) return p;
            return (alt.Price < p.Price) ? alt : p;
        }
    }

}
