/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.weather;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author terminator
 */
public class WeatherData {

    private String city;
    private String weather;
    private String date;
    private float temp;
    private String weatherTag;

    public String getWeatherTag() {
        return weatherTag;
    }

    public void setWeatherTag(String weatherTag) {
        this.weatherTag = weatherTag;
    }

    public WeatherData() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        if (!date.equals("N/A")) {
            Date d = new Date(new Timestamp(Long.parseLong(date) * 1000).getTime());
            String formatdate = new SimpleDateFormat("dd-MM-yyyy").format(d);
            this.date = formatdate;
        } else {
            this.date = date;
        }
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

}
