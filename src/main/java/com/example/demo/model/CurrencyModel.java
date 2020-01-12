package com.example.demo.model;

import java.io.Serializable;

public class CurrencyModel implements Serializable {

    private CurrencyEnum sourceCurrency;
    private CurrencyEnum targetCurrency;
    private Double monetary;


    public CurrencyEnum getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(CurrencyEnum sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public CurrencyEnum getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(CurrencyEnum targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Double getMonetary() {
        return monetary;
    }

    public void setMonetary(Double monetary) {
        this.monetary = monetary;
    }
}
