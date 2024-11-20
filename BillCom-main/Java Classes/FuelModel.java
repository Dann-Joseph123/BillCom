package com.secondapplication.app;

public class FuelModel {
    String fuelType;
    String date;
    String priceInPHP;
    String priceInUSD;

    public FuelModel(String fuelType, String date, String priceInPHP, String priceInUSD) {
        this.fuelType = fuelType;
        this.date = date;
        this.priceInPHP = priceInPHP;
        this.priceInUSD = priceInUSD;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getDate() {
        return date;
    }

    public String getPriceInPHP() {
        return priceInPHP;
    }

    public String getPriceInUSD() {
        return priceInUSD;
    }
}
