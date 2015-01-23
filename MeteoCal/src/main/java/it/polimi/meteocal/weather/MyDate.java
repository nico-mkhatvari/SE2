package it.polimi.meteocal.weather;

import it.polimi.meteocal.entity.Events;
import it.polimi.registration.business.security.entity.User;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author terminator
 */
@Named(value = "mydate")
@RequestScoped

public class MyDate {

    /**
     * @param args the command line arguments
     */
    List<WeatherData> weatherListData;
    List<WeatherData> weatherSunny;
    WeatherData weatherData;

    public void init() {
        Weather np = new Weather();
        Calendar c = new GregorianCalendar();
        System.out.println(c.getTime());
        System.out.println(new Date());
        Date endDate = new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(4));

        Events myEvent = new Events("MyEvent", "Evento di prova", new Date(), endDate, true, true, "Milano", "", new User());
        ////////////////////////////////////////////////////////////////////////////
        //weatherListData = np.getIntervalWeather(myEvent);
        weatherSunny = np.getWeatherSunnyDays(myEvent);
        //weatherData = np.getSingleWeather(new Events());
        //////////////////////////////////////////////////////////////////////////////

        for(WeatherData wd : weatherSunny){
            System.out.println(wd.getCity()
                    + wd.getDate()
                    + wd.getWeather()
                    + wd.getTemp());
        }
    }
}
