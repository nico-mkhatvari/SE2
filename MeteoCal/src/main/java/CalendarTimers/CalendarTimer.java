/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarTimers;

import it.polimi.meteocal.control.EventsEJB;
import it.polimi.meteocal.entity.Events;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
<<<<<<< HEAD
=======

>>>>>>> 8aaee5951e71bf311e94879b2a7df55577ec3c59
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
    
    
    Calendar addXhours(int hours){
        
        Calendar date = Calendar.getInstance();
        date.add(Calendar.HOUR_OF_DAY, hours);
        return date;
        
    }
    
    
    @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
    public void deleteOldEvents(){
        int i = 0;
        
       
<<<<<<< HEAD
        List<Events> expiredEvents = em.createNamedQuery("Events.expiredEvents", Events.class).setParameter(1, new Date(), TemporalType.TIMESTAMP).getResultList();
        List<Events> events24 = em.createNamedQuery("Events.expiredEvents", Events.class).setParameter(1, new Date(), TemporalType.TIMESTAMP).getResultList();
=======
        List<Events> expiredEvents = em.createNamedQuery("Events.expiredEvents", Events.class).setParameter("enddate", mydate, TemporalType.TIMESTAMP).getResultList();
        
>>>>>>> 8aaee5951e71bf311e94879b2a7df55577ec3c59
                
        while(expiredEvents.iterator().hasNext()){
            eventEjb.deleteEvent(expiredEvents.get(i).getId()); //si può get(i++)??
            i++;
        }
    }
    
}
