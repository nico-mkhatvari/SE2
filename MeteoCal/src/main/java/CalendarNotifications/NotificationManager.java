/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarNotifications;

import it.polimi.meteocal.control.EventsEJB;
import it.polimi.meteocal.control.InvitationListEJB;
import it.polimi.meteocal.entity.Events;
import it.polimi.meteocal.entity.InvitationList;
import it.polimi.meteocal.entity.MyNotification;
import it.polimi.meteocal.weather.Weather;
import it.polimi.meteocal.weather.WeatherData;
import it.polimi.registration.business.security.boundary.UserManager;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author terminator
 */
@Named(value = "notificationBean")
@RequestScoped
public class NotificationManager {

    private List<Notification> badEvents;
    @EJB
    private NotificationEJB notificationEJB;
    @EJB
    private InvitationListEJB invitationListEJB;
    @EJB
    private EventsEJB eventsEJB;
    @EJB
    private UserManager um;
    private MyNotification myNotification;
    private List<MyNotification> myNotificationlist;

    @PostConstruct
    public void init() {

        //inizializzazione delle liste
        myNotificationlist = new ArrayList<>();
        myNotification = new MyNotification();

        //ricerca della lista degli eventi a cui partecipo
        List<InvitationList> myParticipation = invitationListEJB.findInvitationListByEmail(um.getLoggedUser().getEmail());
        List<Events> myEvents = new ArrayList<>();
        for (InvitationList il : myParticipation) {
            myEvents.add(il.getEvents());
        }

        //Inizializzazione della lista che contente tutte le notifiche dove l'evento è a rischio maltempo
        badEvents = notificationEJB.findBadEvents(myEvents, um.getLoggedUser());

        for (Notification n : badEvents) {
            Events e = eventsEJB.findEvent(n.getEventid().getId());

            //Se è organizzatore, tra 3 giorni brutto meteo per l'evento, suggerisce il primo giorno di sole disponibile
            if (e.getOrganizer().equals(um.getLoggedUser())) {

                //cerca i primo/i giorno/i di sole              
                String suggetedDay = "";
                Weather w = new Weather();
                List<WeatherData> wdlist = w.getWeatherSunnyDays(e);

                //if any sunny day was found
                if (wdlist.isEmpty()) {
                    suggetedDay = suggetedDay + "Not sunny day found" + "\n";
                }
                for (WeatherData wd : wdlist) {
                    if (wd.getDate() != null) {
                        suggetedDay = suggetedDay + wd.getWeatherTag() + " - " + wd.getDate() + "\n";
                    } else {    //if any sunny day was found
                        suggetedDay = suggetedDay + "Not sunny day found" + "\n";
                    }
                }

                myNotificationlist.add(new MyNotification(n.getId(), e.getId(), n.getUseremail().getEmail(),
                        e.getName(), e.getDescription(), e.getStartdate(), e.getEnddate(), e.getCity(), e.getAddress(),
                        "Bad weather condition for organized event", suggetedDay));
            } else { //altrimenti brutto meteo per l'evento a cui partecipi domani
                myNotificationlist.add(new MyNotification(n.getId(), e.getId(), n.getUseremail().getEmail(),
                        e.getName(), e.getDescription(), e.getStartdate(), e.getEnddate(), e.getCity(), e.getAddress(),
                        "Bad weather condition for tomorrow event"));
            }
        }
    }

    public List<MyNotification> getMyNotificationlist() {
        return myNotificationlist;
    }

    public MyNotification getMyNotification() {
        return myNotification;
    }

    public void setMyNotification(MyNotification myNotification) {
        this.myNotification = myNotification;
    }

    public void setAsReadNotification() {
        Notification searchedNotification = notificationEJB.findNotification(myNotification.getNotificationId());
        searchedNotification.setViewed(true);
        notificationEJB.updateNotification(searchedNotification);
        init();
    }
}
