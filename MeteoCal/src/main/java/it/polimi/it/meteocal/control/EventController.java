/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.it.meteocal.control;

import it.polimi.meteocal.boundary.EventManager;
import it.polimi.meteocal.entity.Event;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author jiasheng
 */
@ManagedBean
@Named(value = "eventBean")
@RequestScoped
public class EventController {

    @EJB
    private EventManager em;

    private Event event;
    
    private Event selectedEvent = new Event();

    private Date currentDate;

    private List<Event> eventlist = new ArrayList<Event>();


    public Date getCurrentDate() {
        if (currentDate == null) {
            currentDate = new Date();
        }
        return currentDate;
    }

    public EventController() {
    }

    public Event getEvent() {
        if (event == null) {
            event = new Event();
        }
        System.out.println("getEvent|||||||||||||||||||||||||||||||||||");
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Event> getEventlist() {
        return eventlist;
    }

    public void setEventlist(List<Event> eventlist) {
        this.eventlist = eventlist;
    }

    public Event getSelectedEvent() {
        
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public String create() {
        em.save(event);
        eventlist = em.findEvents();
        return "view";
    }
    
    public String delete(Event event){
        System.out.println("DELETE**************************");
        em.deleteEvent(event);
        return "index";
    }
}
