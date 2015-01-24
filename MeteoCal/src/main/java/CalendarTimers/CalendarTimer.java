package CalendarTimers;

import CalendarNotifications.Notification;
import CalendarNotifications.NotificationEJB;
import it.polimi.meteocal.control.EventsEJB;
import it.polimi.meteocal.entity.Events;
import it.polimi.meteocal.entity.InvitationList;
import it.polimi.meteocal.weather.Weather;
import it.polimi.meteocal.weather.WeatherData;
import it.polimi.registration.business.security.entity.User;
import java.util.ArrayList;
import java.util.Calendar;
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
    @EJB
    private NotificationEJB NotificationEjb;

    Calendar addXhours(int hours) {

        Calendar date = Calendar.getInstance();
        date.add(Calendar.HOUR_OF_DAY, hours);
        return date;

    }

    @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
    public void deleteOldEvents() {
        int i = 0;

        List<Events> expiredEvents = em.createNamedQuery("Events.expiredEvents", Events.class).setParameter("enddate", addXhours(0), TemporalType.TIMESTAMP).getResultList();
        deleteExpiredEvents(expiredEvents);
        
        List<Events> event24 = em.createNamedQuery("Events.notifyOutdoorEvents", Events.class)
                .setParameter("enddate", addXhours(24), TemporalType.TIMESTAMP)
                .setParameter("outdoor", true)
                .getResultList();
        List<Events> event72 = em.createNamedQuery("Events.notifyOutdoorEvents", Events.class)
                .setParameter("enddate", addXhours(72), TemporalType.TIMESTAMP)
                .setParameter("outdoor", true)
                .getResultList();

        //Check weather RAIN || SNOW || STORM
        List<Events> badEvent24 = checkWeatherConditions(event24);
        List<Events> badEvent72 = checkWeatherConditions(event72);

        if (!badEvent24.isEmpty()) {
            createNotifications(badEvent24);
        }

        if (!badEvent72.isEmpty()) {
            create72Notification(badEvent72);
        }

    }

    private List<Events> checkWeatherConditions(List<Events> events) {
        Weather w;
        List<WeatherData> wd;
        List<Events> badE = new ArrayList<>();
        for (Events e : events) {
            w = new Weather();
            wd = w.getBadWeatherData(e);
            if (!wd.isEmpty()) {
                badE.add(e);
            }
        }
        return badE;
    }

    private void createNotifications(List<Events> events) {
        Notification n;
        List<User> u;
        for (Events e : events) {
            List<InvitationList> list = em.createNamedQuery("findAllParticipatingListWithEventId", InvitationList.class)
                    .setParameter("eventid", e.getId())
                    .setParameter("participate", true)
                    .getResultList();
            for (InvitationList il : list) {
                n = new Notification();
                n.setEventid(e);
                n.setUseremail(il.getUser1());

                List<Notification> sizeResult = em.createNamedQuery("FindSentNotification", Notification.class)
                        .setParameter("eventid", e)
                        .setParameter("useremail", il.getUser1())
                        .getResultList();
                if (sizeResult.isEmpty()) {
                    n.setViewed(false);
                    NotificationEjb.createNotification(n);
                }
            }

        }
    }

    private void create72Notification(List<Events> events) {
        Notification n;
        for (Events e : events) {

            n = new Notification();
            n.setEventid(e);
            n.setUseremail(e.getOrganizer());

            List<Notification> sizeResult = em.createNamedQuery("FindSentNotification", Notification.class)
                    .setParameter("eventid", e)
                    .setParameter("useremail", e.getOrganizer())
                    .getResultList();
            if (sizeResult.isEmpty()) {
                n.setViewed(false);
                NotificationEjb.createNotification(n);
            }
        }

    }
    
    private void deleteExpiredEvents(List<Events> expiredEvents) {

        for (Events e : expiredEvents) {
            eventEjb.deleteEvent(e.getId());
        }

    }

}
