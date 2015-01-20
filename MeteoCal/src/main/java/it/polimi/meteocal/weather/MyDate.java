
package it.polimi.meteocal.weather;



import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;


/**
 *
 * @author terminator
 */

@Named(value="mydate")
@javax.enterprise.context.RequestScoped
public class MyDate{

    /**
     * @param args the command line arguments
     */
   List<WeatherData> weatherListData;
   WeatherData weatherData;

    public MyDate() {
    }
    String c = "Ciao";
    public String getC(){
        return this.c;
    }
   @PostConstruct
    public void init(){    
    Weather np = new Weather();
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DATE, 17);
    
    ////////////////////////////////////////////////////////////////////////////
    weatherListData = np.getIntervalWeather(Calendar.getInstance(), c, "Milano");
    weatherData = np.getSingleWeather(c, "Milano");
    //////////////////////////////////////////////////////////////////////////////
    
    while(weatherListData.iterator().hasNext())    
    System.out.println(weatherListData.iterator().next().getCity() 
            + weatherListData.iterator().next().getDate() 
            + weatherListData.iterator().next().getWeather() 
            + weatherListData.iterator().next().getTemp()) ;
        
    }
}
