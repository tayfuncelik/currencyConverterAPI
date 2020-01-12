package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum CurrencyEnum implements Serializable {

    CAD("CAD"),
    HKD("HKD"),
    ISK("ISK"),
    PHP("PHP"),
    DKK("DKK"),
    HUF("HUF"),
    CZK("CZK"),
    AUD("AUD"),
    RON("RON"),
    SEK("SEK"),
    IDR("IDR"),
    INR("INR"),
    BRL("BRL"),
    RUB("RUB"),
    HRK("HRK"),
    JPY("JPY"),
    THB("THB"),
    CHF("CHF"),
    SGD("SGD"),
    PLN("PLN"),
    BGN("BGN"),
    TRY("TRY"),
    CNY("CNY"),
    NOK("NOK"),
    NZD("NZD"),
    ZAR("ZAR"),
    USD("USD"),
    MXN("MXN"),
    ILS("ILS"),
    GBP("GBP"),
    KRW("KRW"),
    MYR("MYR"),
    EUR("EUR");

    @JsonValue
    private final String displayName;

    CurrencyEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
