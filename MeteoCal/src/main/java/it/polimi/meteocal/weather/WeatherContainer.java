/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.weather;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.json.JsonArray;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author terminator
 */
@Path("/w")
public class WeatherContainer {

    //LISTA CONTENTE PREVISIONI
    private List<WeatherData> weatherDlist = new ArrayList<>();
    private List<WeatherData> badWeather = new ArrayList<>();
    private String city;
    
    private int eventWindow = 0;
    private int availableWindow = 0;
    private int endwindow;
    private List<WeatherData> nextSunnyForecast;

    private Client client;
    private WebTarget curtarget;
    private int startIndex, endIndex;
    private JsonArray jWeatherArray;
    private JsonObject json;

    private final int DAY = 1000 * 60 * 60 * 24;

    public WeatherContainer() {
        //response = invocation.invoke();

    }

    public void initSingleJson(Calendar startDate, Calendar endDate, String city) {

        client = ClientBuilder.newClient();

        curtarget = client.target("http://api.openweathermap.org/data/2.5/forecast/daily")
                .queryParam("q", city)
                .queryParam("cnt", "16")
                .queryParam("mode", "json")
                .queryParam("units", "metric");

        json = curtarget.request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        startIndex = setDateIndex(startDate);
        endIndex = setDateIndex(endDate);
        controllForecastWindow(startDate, endDate);

        procJson(json);
        extractWeatherData();
        extractBadWeatherData();
        extractNextGoodWeather();

    }

    //gets the start/end index of weather list
    private int setDateIndex(Calendar s) {

        int i = (int) ((s.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / DAY);
        if (i >= 15) {
            i = 15 - 1;
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }

    //fetches forecast
    private void procJson(JsonObject json) {

        jWeatherArray = json.getJsonArray("list");
        city = json.getJsonObject("city").getString("name");

    }

    private void extractWeatherData() {
        //RAIN || SNOW || STORM
        JsonObject jtemp;
        WeatherData wdata;

        for (int i = startIndex; i <= endIndex; i++) {

            jtemp = jWeatherArray.getJsonObject(i);
            wdata = new WeatherData();

            wdata.setWeather(jtemp.getJsonArray("weather").getJsonObject(0).getString("description"));
            wdata.setWeatherCond(jtemp.getJsonArray("weather").getJsonObject(0).getString("main"));
            wdata.setDate(jtemp.get("dt").toString());
            wdata.setCity(city);
            wdata.setTemp(Float.parseFloat(jtemp.getJsonObject("temp").get("day").toString()));

            weatherDlist.add(wdata);
        }
    }

    private void extractBadWeatherData() {
        //RAIN || SNOW || STORM
        JsonObject jtemp;
        WeatherData wdata;

        for (int i = startIndex; i <= endIndex; i++) {

            jtemp = jWeatherArray.getJsonObject(i);
            String weather = jtemp.getJsonArray("weather").getJsonObject(0).getString("main");

            //Add only bad weather
            if (findSubstring("rain", weather) || findSubstring("storm", weather) || findSubstring("snow", weather)) {

                wdata = new WeatherData();
                wdata.setWeatherCond(jtemp.getJsonArray("weather").getJsonObject(0).getString("main"));
                wdata.setWeather(jtemp.getJsonArray("weather").getJsonObject(0).getString("description"));
                wdata.setDate(jtemp.get("dt").toString());
                wdata.setCity(city);
                wdata.setTemp(Float.parseFloat(jtemp.getJsonObject("temp").get("day").toString()));
                badWeather.add(wdata);

            }
        }
    }

    private boolean findSubstring(String subString, String fullString) {
        boolean findit = false;

        for (int i = 0; i < (fullString.length() - subString.length()); i++) {
            if (fullString.regionMatches(true, i, subString, 0, subString.length())) {
                findit = true;
            }
        }

        return findit;
    }

    private void controllForecastWindow(Calendar startDate, Calendar endDate) {

        eventWindow = (int) (endDate.getTimeInMillis() - startDate.getTimeInMillis()) / DAY;
        availableWindow = (int) (Calendar.getInstance().getTimeInMillis() + 14 * DAY - endDate.getTimeInMillis()) / DAY;
        endwindow = (int) (Calendar.getInstance().getTimeInMillis() + 14 * DAY) / DAY;

    }

    private void extractNextGoodWeather() {
        //if event window
        for (int i = endIndex; i <= endwindow && availableWindow >= eventWindow; i++) {

            JsonObject jtemp;
            WeatherData wdata;
            jtemp = jWeatherArray.getJsonObject(i);
            String weather = jtemp.getJsonArray("weather").getJsonObject(0).getString("main");

            //Add clouds or sun
            if (!findSubstring("rain", weather) && !findSubstring("storm", weather) && !findSubstring("snow", weather)) {

                wdata = new WeatherData();
                wdata.setWeatherCond(jtemp.getJsonArray("weather").getJsonObject(0).getString("main"));
                wdata.setWeather(jtemp.getJsonArray("weather").getJsonObject(0).getString("description"));
                wdata.setDate(jtemp.get("dt").toString());
                wdata.setCity(city);
                wdata.setTemp(Float.parseFloat(jtemp.getJsonObject("temp").get("day").toString()));
                nextSunnyForecast.add(wdata);

            } else {

                availableWindow--;
            }

        }
        if (availableWindow < eventWindow) {
            WeatherData wtemp = new WeatherData();
            wtemp.setWeather("N/A");
            wtemp.setDate("N/A");
            wtemp.setCity(city);
            wtemp.setTemp(0);
            wtemp.setWeatherCond("N/A");
            nextSunnyForecast = new ArrayList<>();
            nextSunnyForecast.add(wtemp);
        }
    }

    public List<WeatherData> getWeatherDlist() {
        return weatherDlist;
    }

    public List<WeatherData> getBadWeather() {
        return badWeather;
    }

    public List<WeatherData> getNextSunnyForecast() {
        return nextSunnyForecast;
    }
    
    

}
