/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.weather;

import it.polimi.meteocal.entity.Events;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 *
 * @author terminator
 */
public class Weather {

    private Calendar date, endDate, startDate;
    private String city;

    WeatherData getSingleWeather(Events e) {
        
        processEvents(e);
        WeatherContainer wc = new WeatherContainer();
        wc.initSingleJson(date, date, city);
        
        return wc.getWeatherDlist().get(0);
    }

    List<WeatherData> getIntervalWeather(Events e) {
        
        processEvents(e);
        WeatherContainer wc = new WeatherContainer();
        wc.initSingleJson(startDate, endDate, city);
        
        return wc.getWeatherDlist();
    }

    List<WeatherData> getBadWeatherData(Events e) {
        
        processEvents(e);
        WeatherContainer wc = new WeatherContainer();
        wc.initSingleJson(startDate, endDate, city);
        
        return wc.getBadWeather();
    }
    
    List<WeatherData> getWeatherSunnyDays(Events e) {
        
        processEvents(e);
        WeatherContainer wc = new WeatherContainer();
        wc.initSingleJson(startDate, endDate, city);
        
        return wc.getNextSunnyForecast();
    }
    
    //Methods for pre-processing
    private Calendar dateToCalendar(Date date) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+1"));
        System.out.println(cal.getTime());
        cal.setTime(date);
        return cal;
    }

    void processEvents(Events e){

        date = dateToCalendar(e.getStartdate());
        startDate = dateToCalendar(e.getStartdate());
        endDate = dateToCalendar(e.getEnddate());
        city = e.getCity();
    }
}
