package cz.muni.fi.pv168.project.model;

public class CurrencyDbModel {
    private int currencyId;
    private String name;
    private String tag;
    private double rate;

    public CurrencyDbModel(int currencyId, String name, String tag, double rate) {
        this.currencyId = currencyId;
        this.tag = tag;
        this.name = name;
        this.rate = rate;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
