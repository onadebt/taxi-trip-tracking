package cz.muni.fi.pv168.project.ui.model;

public class Currency {
    private String name;
    private String currencyCode;
    private double amountPerDefault;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getAmountPerDefault() {
        return amountPerDefault;
    }

    public void setAmountPerDefault(double amountPerDefault) {
        this.amountPerDefault = amountPerDefault;
    }
}
