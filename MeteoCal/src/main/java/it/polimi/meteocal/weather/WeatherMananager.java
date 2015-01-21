/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.weather;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author jiasheng
 */
@Named(value = "weatherBean")
@RequestScoped
public class WeatherMananager {

    private List<WeatherData> weatherDataList = new ArrayList<>();
    private Weather w = new Weather();

    public WeatherMananager() {
    }
    
    public List<WeatherData> getWeatherDataList(Date startDate, Date endDate, String city) {
        
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        
        //checks if start date and end date have the same day
        if (startCal.get(Calendar.YEAR) == endCal.get(Calendar.YEAR) &&
            startCal.get(Calendar.DAY_OF_YEAR) == endCal.get(Calendar.DAY_OF_YEAR)) {
            WeatherData weatherData = w.getSingleWeather(startCal, city);
            weatherDataList.add(weatherData);
        } else {
            weatherDataList = w.getIntervalWeather(startCal, endCal, city);
        }
        return weatherDataList;
    }

    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }
    
    
}
