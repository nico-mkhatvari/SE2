/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarTimers;

import it.polimi.meteocal.control.EventsEJB;
import it.polimi.meteocal.entity.Events;
import it.polimi.meteocal.weather.MyDate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.util.logging.Logger;
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
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date mydate = new Date();
    String date = sdf.format(mydate);
    
    @Schedule(second = "*", minute = "*", hour = "*", persistent = false)
    public void deleteOldEvents(){
        int i = 0;
        
       
        List<Events> expiredEvents = em.createNamedQuery("Events.expiredEvents", Events.class).setParameter(1, mydate, TemporalType.TIMESTAMP).getResultList();
        
                
        while(expiredEvents.iterator().hasNext()){
            eventEjb.deleteEvent(expiredEvents.get(i).getId()); //si può get(i++)??
            i++;
        }
    }
    
}
