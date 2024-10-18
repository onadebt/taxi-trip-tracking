package cz.muni.fi.pv168.project.ui.model;

import java.sql.Timestamp;

public class RideModel {
    private double amountCurrency;
    private String currency;
    private double distance;
    private String categoryName;
    private Timestamp createdDate;

    // Constructor
    public RideModel(double amountCurrency, String currency, double distance, String categoryName, Timestamp createdDate) {
        this.amountCurrency = amountCurrency;
        this.currency = currency;
        this.distance = distance;
        this.categoryName = categoryName;
        this.createdDate = createdDate;
    }

    // Getters
    public double getAmountCurrency() {
        return amountCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public double getDistance() {
        return distance;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }
}
