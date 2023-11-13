package com.example.metactivity;

import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    private double temperature;
    private double tempMin;
    private double tempMax;

    public double getTemperature() {
        return temperature;
    }
}
