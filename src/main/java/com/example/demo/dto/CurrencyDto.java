package com.example.demo.dto;

import com.example.demo.model.CurrencyEnum;

public class CurrencyDto {

    private CurrencyEnum source;
    private CurrencyEnum target;
    private Double value;

    public CurrencyEnum getSource() {
        return source;
    }

    public void setSource(CurrencyEnum source) {
        this.source = source;
    }

    public CurrencyEnum getTarget() {
        return target;
    }

    public void setTarget(CurrencyEnum target) {
        this.target = target;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
