package com.secondapplication.app;

public class Appliance {
    private int id;
    private String applianceName;
    private float electricCapacity;
    private float hoursUsed;
    private float daysUsed;
    public static float electricityCost;

    public Appliance(int id, String name, float electricCapacity, float hoursUsed, float daysUsed) {
        this.id = id;
        this.applianceName = name;
        this.electricCapacity = electricCapacity;
        this.hoursUsed = hoursUsed;
        this.daysUsed = daysUsed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public float getElectricCapacity() {
        return electricCapacity;
    }

    public void setElectricCapacity(float electricCapacity) {
        this.electricCapacity = electricCapacity;
    }

    public float getHoursUsed() {
        return hoursUsed;
    }

    public void setHoursUsed(float hoursUsed) {
        this.hoursUsed = hoursUsed;
    }

    public float getDaysUsed() {
        return daysUsed;
    }

    public void setDaysUsed(float daysUsed) {
        this.daysUsed = daysUsed;
    }

    @Override
    public String toString() {
        return " Appliance " +
                " id: " + id +
                "\n name: " + applianceName  +
                "\n Electric Capacity (watts)" + electricCapacity +
                "\n Hours Used (per day): " + hoursUsed +
                "\n Days Used (per week): " + daysUsed +
                "\n Energy consumption per day: " + this.energyConsumptionCalculationperDay() +
                "\n Electricity cost per day: " + this.electricityCostCalculationperDay() +
                "\n Electricity cost per week: " + this.electricityCostCalculationWeek() +
                ' ';
    }

    public float energyConsumptionCalculationperDay (){
        float pwrConsumption = this.getElectricCapacity();
        float hr = this.getHoursUsed();
        float wattDay = (pwrConsumption * hr);   //watt day
        float kilowattDay = wattDay / 1000;      //kilowatt day unit: kwH
        return kilowattDay;
    }

    public float electricityCostCalculationperDay (){
        float kwhPerDay = this.energyConsumptionCalculationperDay();
        float electricityCostPerDay = kwhPerDay * electricityCost;
        return electricityCostPerDay;
    }

    public float electricityCostCalculationWeek (){
        float electricityCostPerDay = this.electricityCostCalculationperDay();
        float days = this.getDaysUsed();
        float electricityCostCalculationperWeek = electricityCostPerDay * days;
        return electricityCostCalculationperWeek;
    }
}