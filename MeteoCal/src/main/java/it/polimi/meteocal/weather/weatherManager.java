/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.weather;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.Serializable;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;


/**
 * Weather:
 * https://api.accuweather.com/
 * http://www.wunderground.com/weather/api
 * http://www.worldweatheronline.com/api/compare.aspx
 * https://developer.forecast.io/docs/v2
 * http://openweathermap.org/api
 * 
 * Resources
 * http://www.jsonschema2pojo.org/
 * http://java-buddy.blogspot.it/2013/07/parse-json-with-java-se-and-java-json.html
 * https://github.com/migtavares/owmClient
 * https://github.com/mstahv/consuming-rest-apis/blob/master/src/main/java/org/example/VaadinUI.java
 * 
 * References
 * http://code.tutsplus.com/tutorials/create-a-weather-app-on-android--cms-21587
 * http://www.primefaces.org/showcase/mobile/weather.xhtml
 * https://vaadin.com/blog/-/blogs/consuming-rest-services-from-java-applications
 * http://www.survivingwithandroid.com/2013/05/build-weather-app-json-http-android.html
 * 
 * RESTful
 * http://crunchify.com/create-very-simple-jersey-rest-service-and-send-json-data-from-java-client/
 * http://crunchify.com/how-to-build-restful-service-with-java-using-jax-rs-and-jersey/
 */
@Named(value = "weatherView")
@ViewScoped
public class weatherManager implements Serializable{

    private String conditions;
    private String city;
    private String unit = "c";
    private Map<String,String> cities;
     
    private static final Logger logger = Logger.getLogger(weatherManager.class.getName());
 
    /**
     *PostConstruct callbacks happen after a bean instance is instantiated in the EJB container. If the bean is using
any dependency injection mechanisms for acquiring references to resources or other objects in its environment,
PostConstruct will occur after injection is performed and before the first business method in the bean class is
called.
     * 
     */
    @PostConstruct
    public void init() {
        cities = new LinkedHashMap<String, String>();
        cities.put("Istanbul", "TUXX0014");
        cities.put("Barcelona", "SPXX0015");
        cities.put("London", "UKXX0085");
        cities.put("New York", "USNY0996");
        cities.put("Paris", "FRXX2071");
        cities.put("Rome", "ITXX0067");
    }
 
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
 
    public String getConditions() {
        return conditions;
    }
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
         
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
 
    public Map<String, String> getCities() {
        return cities;
    }
 
    public void retrieveConditions() {
        try {
            URL feedSource = new URL("http://weather.yahooapis.com/forecastrss?p=" + city + "&u=" + unit);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedSource));
            String value = ((SyndEntry) feed.getEntries().get(0)).getDescription().getValue();
             
            conditions = value.split("<a href")[0];
        } catch (Exception e) {
            logger.severe(e.getMessage());
            conditions = "Unable to retrieve weather forecast at the moment.";
        }
    }
 
    public String saveSettings() {
        conditions = null;
        return "pm:main";
    }
    
}
