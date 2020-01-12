package com.example.demo.dto;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CurrencyApiResponse {

    private Map<String, String> rates;
    private String base;
    private String date;

    public Map<String, String> getRates() {
        return rates;
    }

    public void setRates(Map<String, String> rates) {
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
