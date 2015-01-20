/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.weather;

import java.util.Calendar;
import java.util.List;


/**
 *
 * @author terminator
 */
public class Weather {
    
    WeatherData getSingleWeather(Calendar date, String city) {
        
        WeatherContainer wc = new WeatherContainer();
        wc.initSingleJson(date, date, city);
        return wc.getWeatherDlist().get(0);
    }
    
    List<WeatherData> getIntervalWeather(Calendar starDate, Calendar endDate, String City) {
        
        WeatherContainer wc = new WeatherContainer();
        wc.initSingleJson(starDate, endDate, City);
        return wc.getWeatherDlist();
    }
    
}
