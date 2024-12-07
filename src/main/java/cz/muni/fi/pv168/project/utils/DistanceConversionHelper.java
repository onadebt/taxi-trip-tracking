package cz.muni.fi.pv168.project.utils;

import cz.muni.fi.pv168.project.model.enums.DistanceUnit;

public class DistanceConversionHelper {
    private static final double milesPerKilometer = 1.609;
    public static double kilometersToMiles(double kilometers) {
        return kilometers / milesPerKilometer;
    }

    public static double milesToKilometers(double miles) {
        return miles * milesPerKilometer;
    }


    public static double convertDistance(double distance, DistanceUnit currentUnit, DistanceUnit newUnit) {
        if (currentUnit == newUnit) {
            return distance;
        }
        if (currentUnit == DistanceUnit.KILOMETER) {
            return kilometersToMiles(distance);
        } else {
            return milesToKilometers(distance);
        }
    }
}
