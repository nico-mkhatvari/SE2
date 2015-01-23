/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.weather;

import it.polimi.meteocal.control.EventsEJB;
import it.polimi.meteocal.entity.Events;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author jiasheng
 */
@Named(value = "weatherManager")
@RequestScoped
public class WeatherMananager {

    @EJB
    private EventsEJB eEJB;
    private List<WeatherData> weatherDataList = new ArrayList<>();
    private Weather w = new Weather();

    public void getWeather(int eId){
    Events e = eEJB.findEvent(eId);
    weatherDataList = w.getIntervalWeather(e);
    }

    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }
    
    
}
