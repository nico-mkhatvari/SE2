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
import it.polimi.registration.business.security.boundary.UserManager;
import java.util.ArrayList;
import java.util.Date;
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

                //inserire metodo che, dato l'eventid, cerca primo giorno di sole e ritorna una stringa o un date                
                Date suggetedDay = new Date(); //test

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
        notificationEJB.deleteNotification(searchedNotification.getId());
        init();
    }
}
