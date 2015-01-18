/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.weather;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;

import javax.json.stream.JsonParser;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.json.JsonArray;

/**
 * REST Web Service
 *
 * @author terminator
 */
@Path("/ws")
@Named("weatherContainer")

@RequestScoped
public class WeatherContainer {

    @Context
    private UriInfo context;
    @Inject
    WeatherData weatherData;

    Client client;
    WebTarget curtarget, foretarget;
    Response response;
    JsonParser jsonParser;

    String city;
    String weathercond;
    int temp;
    List<WeatherData> weatherDlist = new ArrayList<>();

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    public List<WeatherData> getWeatherDlist() {
        return weatherDlist;
    }

    public void setWeatherDlist(List<WeatherData> weatherDlist) {
        this.weatherDlist = weatherDlist;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeathercond() {
        return weathercond;
    }

    public void setWeathercond(String weathercond) {
        this.weathercond = weathercond;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    /**
     * Creates a new instance of WeatherContainer
     */
    public WeatherContainer() {
        //response = invocation.invoke();

    }

    @PostConstruct
    protected void init() {
        client = ClientBuilder.newClient();
        //example query params: ?q=Turku&cnt=10&mode=json&units=metric
        curtarget = client.target(
                "http://api.openweathermap.org/data/2.5/weather").queryParam("q", "Moscow")
                .queryParam("cnt", "10")
                .queryParam("mode", "json")
                .queryParam("units", "metric");
        
        foretarget = client.target(
                "http://api.openweathermap.org/data/2.5/forecast/daily").queryParam("cnt", "10")
                .queryParam("mode", "json")
                .queryParam("q", "Milano")
                .queryParam("units", "metric");

    }

    public void currentWeather() {
        //current weather
        processJson(curtarget.request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class));
        
        //forecast
        processJson(foretarget.request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class));
        
    }

    public void forecastWeather() {

        processJson(foretarget.request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class));
    }

    public void processJson(JsonObject json) {

        JsonObject jweather, jtemp;
        JsonArray jaweather;
        WeatherData wdata;
        JsonArray jlist = json.getJsonArray("list");
        
        
        if (json.containsKey("list")) {
            
            this.city = json.getJsonObject("city").getString("name");
            
            for (int i = 0; i < jlist.size(); i++) {
                
                jtemp = jlist.getJsonObject(i);
                jaweather = jtemp.getJsonArray("weather");
                
                wdata = new WeatherData();
                
                wdata.setWeather(jtemp.getJsonArray("weather").getJsonObject(0).getString("description"));
                wdata.setDate(jtemp.get("dt").toString());
                wdata.setCity(json.getJsonObject("city").getString("name"));
                wdata.setTemp(Float.parseFloat(jtemp.getJsonObject("temp").get("day").toString()));
                
                weatherDlist.add(wdata);
            }
        } else {

            this.weatherData.setWeather(json.getJsonArray("weather").getJsonObject(0).getString("description"));
            this.weatherData.setDate(json.get("dt").toString());
            this.weatherData.setCity(json.getString("name"));
            this.weatherData.setTemp(Float.parseFloat(json.getJsonObject("main").get("temp").toString()));
            System.out.println();
        }

    }

    /**
     * Retrieves representation of an instance of
     * it.polimi.meteocal.weather.WeatherContainer
     *
     * @return an instance of java.lang.String
     */
    //Invocation.Builder allows you to build a GET method as well as POST: client request invocation.
    //Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildGet();
    /*
     You then need to call the invoke()method to actually invoke your remote
     RESTful web service and get a Response object back. The Response is what defines
     the contract with the returned instance and is what you will consume
     */
    //Synchronously invoke the request and receive a response back.
}
