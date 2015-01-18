/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this teventEjbplate file, choose Tools | TeventEjbplates
 * and open the teventEjbplate in the editor.
 */
package it.polimi.meteocal.boundary;

import it.polimi.meteocal.control.EventsEJB;
import it.polimi.meteocal.entity.Events;
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
@Named(value = "eventsBean")
@RequestScoped
public class EventsManager {

    @EJB
    private EventsEJB eventsEjb;
    private Events events;
    private List<Events> eventlist;

    public EventsManager() {
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    public List<Events> getEventlist() {
        return eventlist;
    }

    public void setEventlist(List<Events> eventlist) {
        this.eventlist = eventlist;
    }

    @PostConstruct
    public void init() {
        eventlist = eventsEjb.findEvents();
    }

    
}
