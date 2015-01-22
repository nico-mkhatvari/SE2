
package CalendarTimers;
import it.polimi.meteocal.control.EventsEJB;
import it.polimi.meteocal.entity.Events;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

/**
 *
 * @author terminator
 */


@Startup
@Singleton

public class CalendarTimer {

    @PersistenceContext
    EntityManager em;
    @EJB
    private EventsEJB eventEjb;

    Calendar addXhours(int hours) {

        Calendar date = Calendar.getInstance();
        date.add(Calendar.HOUR_OF_DAY, hours);
        return date;

    }

    @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
    public void deleteOldEvents() {
        int i = 0;

        List<Events> expiredEvents = em.createNamedQuery("Events.expiredEvents", Events.class).setParameter("enddate", addXhours(0), TemporalType.TIMESTAMP).getResultList();
        List<Events> event24 = em.createNamedQuery("Events.expiredEvents", Events.class).setParameter("enddate", addXhours(24), TemporalType.TIMESTAMP).getResultList();
        List<Events> event72 = em.createNamedQuery("Events.expiredEvents", Events.class).setParameter("enddate", addXhours(72), TemporalType.TIMESTAMP).getResultList();
        deleteExpiredEvents(expiredEvents);
        
        //Check weather RAIN || SNOW || STORM
        //List<Events> badEvent = checkWeatherConditions(event24);
        //createNotifications(badEvent);
        
    }
    
    private List<Events> checkWeatherConditions(List<Events> events){
        
        return events;
    }
    
    private void createNotifications(List<Events> events){
        
    }

    private void deleteExpiredEvents(List<Events> expiredEvents) {
        
        for(Events e : expiredEvents) {
            eventEjb.deleteEvent(e.getId());
        }
        
    }

}
