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
    List<WeatherData> weatherDlist = new ArrayList<>();
    String city;

    Client client;
    WebTarget curtarget;
    Response response;
    JsonParser jsonParser;

    String weathercond;
    int temp;
    private int startIndex, endIndex;

    private final int DAY = 1000 * 60 * 60 * 24;
    private final int HOUR = 1000 * 60 * 60;

    /**
     * Creates a new instance of WeatherContainer
     */
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

        JsonObject json = curtarget.request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        startIndex = setDateIndex(startDate);
        endIndex = setDateIndex(endDate);
        
        procJson(json);

    }

    //gets the start/end index of weather list
    int setDateIndex(Calendar s) {

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
    public void procJson(JsonObject json) {

        JsonObject jweather, jtemp;
        JsonArray jaweather;
        WeatherData wdata;
        JsonArray jlist = json.getJsonArray("list");
        Calendar tempcl = Calendar.getInstance();
        this.city = json.getJsonObject("city").getString("name");

        for (int i = startIndex; i <= endIndex; i++) {

            jtemp = jlist.getJsonObject(i);

            jaweather = jtemp.getJsonArray("weather");

            wdata = new WeatherData();

            wdata.setWeather(jtemp.getJsonArray("weather").getJsonObject(0).getString("description"));
            wdata.setDate(jtemp.get("dt").toString());
            wdata.setCity(json.getJsonObject("city").getString("name"));
            wdata.setTemp(Float.parseFloat(jtemp.getJsonObject("temp").get("day").toString()));

            weatherDlist.add(wdata);
        }
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

}
