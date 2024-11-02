package cz.muni.fi.pv168.project.model;

import java.sql.Timestamp;

public class RideDbModel {
    private int rideId;
    private double amountCurrency;
    private int currencyId;
    private double distance;
    private int categoryId;
    private int passengers;
    private TripType tripType;
    private Timestamp createdDate;
}
