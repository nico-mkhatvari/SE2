/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.boundary;

import it.polimi.meteocal.control.InvitationListEJB;
import it.polimi.meteocal.entity.Events;
import it.polimi.meteocal.entity.InvitationList;
import it.polimi.meteocal.entity.MyScheduleEvent;
import it.polimi.registration.business.security.boundary.UserManager;
import it.polimi.registration.business.security.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jiasheng
 */
@Named(value = "inbox")
@RequestScoped
public class Inbox {

    private MyScheduleEvent scheduleEvent = new MyScheduleEvent();
    @EJB
    private UserManager um;
    @EJB
    private InvitationListEJB invitationListEJB;
    private List<Events> myEventlist = new ArrayList<>();
    private List<InvitationList> onInvitationlist;
    private List<User> userlist = new ArrayList<>();
    private Events selectedEvent = new Events();
    private List<InvitationList> list;

    @PostConstruct
    public void init() {
        //Inizializza la lista contente tutti gli inviti dove è presente l'utente loggato e non è già partecipante 
        onInvitationlist = invitationListEJB.findNotParticipatingListByEmail(um.getLoggedUser().getEmail());
        
        //Estrazione di Events da InvitationList dove è presente l'utente loggato
        for (int i = 0; i < onInvitationlist.size(); i++) {
            InvitationList tempList = onInvitationlist.get(i);
            myEventlist.add(tempList.getEvents());
        }
    }

    public List<Events> getMyEventlist() {
        return myEventlist;
    }

    public Events getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Events selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public List<User> getUserlist() {
        return userlist;
    }

    public List<InvitationList> getOnInvitationlist() {
        return onInvitationlist;
    }

    public void buttonAction() {
        //ottengo la lista degli inviti riguardo all'evento selezionato
        list = invitationListEJB.findInvitationList(selectedEvent.getId());
        for (int i = 0; i < list.size(); i++) {
            InvitationList tempList = list.get(i);
            User tempUser = tempList.getUser1();
            if (!selectedEvent.getOrganizer().equals(tempUser)) {
                userlist.add(tempList.getUser1());
            }
        }
    }

    public String accept() {
        invitationListEJB.acceptInvitation(selectedEvent, um.getLoggedUser());
        init();
        return "inbox?faces-redirect=true";
    }

    public String decline() {
        invitationListEJB.declineInvitation(selectedEvent, um.getLoggedUser());
        init();
        return "inbox?faces-redirect=true";
    }

}
