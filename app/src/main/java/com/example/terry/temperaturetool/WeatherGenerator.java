package com.example.terry.temperaturetool;

import java.util.Random;

/**
 * Created by Terry on 10/4/2016.
 *
 * this class is a tool to generate a list of random temperature
 */

public class WeatherGenerator {
    private double[] weatherList;

    public WeatherGenerator(){
        //TODO:constuctor
        this.weatherList = new double[5];
        generateWeatherList();
    }

    protected void generateWeatherList() {
        //TODO:generate a list of temperature for 5 days
        Random Rn = new Random();
        int Average = 5;
        int threshold = 30;// the threshold is decided the floating range of each day's temperature
        for(int i = 0; i<5; i++){
            this.weatherList[i] = Rn.nextInt()%threshold + Average;
        }
    }

    public double[] getWeatherList() {
        return weatherList;
    }
}
