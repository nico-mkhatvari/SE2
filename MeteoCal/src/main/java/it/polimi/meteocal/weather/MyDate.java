package it.polimi.meteocal.weather;

import it.polimi.meteocal.entity.Events;
import it.polimi.registration.business.security.entity.User;
import java.util.Calendar;
import java.util.Date;
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
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 17);
        Date endDate = new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(2));

        Events myEvent = new Events("MyEvent", "Evento di prova", new Date(), endDate, true, true, "Milano", "", new User());
        ////////////////////////////////////////////////////////////////////////////
        weatherListData = np.getIntervalWeather(myEvent);
        weatherSunny = np.getIntervalWeather(myEvent);
    //weatherData = np.getSingleWeather(new Events());
        //////////////////////////////////////////////////////////////////////////////
        Iterator<WeatherData> it = weatherListData.iterator();
        while (it.hasNext()) {
            System.out.println(weatherListData.iterator().next().getCity()
                    + weatherListData.iterator().next().getDate()
                    + weatherListData.iterator().next().getWeather()
                    + weatherListData.iterator().next().getTemp());
        }

        it = weatherSunny.iterator();
        while (it.hasNext()) {
            System.out.println(weatherSunny.iterator().next().getCity()
                    + weatherSunny.iterator().next().getDate()
                    + weatherSunny.iterator().next().getWeather()
                    + weatherSunny.iterator().next().getTemp());
        }
    }
}
