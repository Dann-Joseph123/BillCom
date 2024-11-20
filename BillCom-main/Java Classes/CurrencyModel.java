package com.secondapplication.app;

public class CurrencyModel {
    private String currency;
    private String toPeso;
    private String inv;

    public CurrencyModel(String currency, String toPeso, String inv) {
        this.currency = currency;
        this.toPeso = toPeso;
        this.inv = inv;
    }

    public String getCurrency() {
        return currency;
    }

    public String getToPeso() {
        return toPeso;
    }

    public String getInv() {
        return inv;
    }

    @Override
    public String toString() {
        return "CurrencyModel{" +
                "currency='" + currency + '\'' +
                ", toPeso='" + toPeso + '\'' +
                ", inv='" + inv + '\'' +
                '}';
    }
}