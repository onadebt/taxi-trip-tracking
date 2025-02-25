package cz.muni.fi.pv168.project.model;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

public class Currency extends Entity {
    //    private @Nullable Long id;
    private String name;
    private String code;
    private BigDecimal exchangeRate;

    public Currency(@Nullable Long id, String name, String code, BigDecimal exchangeRate) {
//        this.id = id;
        super(id);
        setName(name);
        setCode(code);
        setExchangeRate(exchangeRate);
    }


//    public @Nullable Long getId() {
//        return id;
//    }
//
//    public void setId(@Nullable Long id) {
//        this.id = id;
//    }


    public Currency() {
        super(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return "Currency{" +
//                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", currencyCode='" + code + '\'' +
                ", exchangeRate='" + exchangeRate + '\'' +
                '}';
    }
}
