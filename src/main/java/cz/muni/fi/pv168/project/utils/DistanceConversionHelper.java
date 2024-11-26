package cz.muni.fi.pv168.project.utils;

public class DistanceConversionHelper {
    private static final double milesPerKilometer = 1.609;
    public static double kilometersToMiles(double kilometers) {
        return kilometers / milesPerKilometer;
    }

    public static double milesToKilometers(double miles) {
        return miles * milesPerKilometer;
    }
}
