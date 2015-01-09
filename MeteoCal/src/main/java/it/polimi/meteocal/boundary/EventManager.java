/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.boundary;

import it.polimi.meteocal.control.EventEJB;
import it.polimi.meteocal.entity.Event;
import java.util.Date;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;   
import javax.inject.Named;


/**
 *
 * @author jiasheng
 */
@ManagedBean
@Named(value = "eventBean")
@RequestScoped
public class EventManager{

    @EJB
    private EventEJB eventEjb;

    private Event event;
    
    private Event selectedEvent;

    private Date currentDate;

    private List<Event> eventlist;


    public Date getCurrentDate() {
        if (currentDate == null) {
            currentDate = new Date();
        }
        return currentDate;
    }

    public EventManager() {
    }

    public Event getEvent() {
        if (event == null) {
            event = new Event();
        }
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
    //@PostConstruct
    public String create() {
        eventEjb.save(event);
        eventlist = eventEjb.findEvents();
        return "view";
    }
    
    @PostConstruct
    public void findEvents(){
        eventlist = eventEjb.findEvents();
    }
    
    public String delete(int id){
       
        eventEjb.deleteEvent(id);
        return "index";
    }
}
