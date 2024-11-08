package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.CurrencyDbModel;

public class Currency {
    private int id = 0;
    private String name;
    private String currencyCode;
    private double exchangeRate;

    public Currency() {};
    public Currency(CurrencyDbModel currencyDbModel) {
        this.id = currencyDbModel.getCurrencyId();
        this.name = currencyDbModel.getName();
        this.currencyCode = currencyDbModel.getTag();
        this.exchangeRate = currencyDbModel.getRate();
    }

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

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public int getId() {
        return id;
    }
}
