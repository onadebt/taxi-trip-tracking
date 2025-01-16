package cz.muni.fi.pv168.project.model.DbModels;

import java.math.BigDecimal;

public class CurrencyDbModel {
    private Long currencyId;
    private String name;
    private String tag;
    private BigDecimal rate;

    public CurrencyDbModel(Long currencyId, String name, String tag, BigDecimal rate) {
        this.currencyId = currencyId;
        this.tag = tag;
        this.name = name;
        this.rate = rate;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
