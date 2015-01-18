/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this teventEjbplate file, choose Tools | TeventEjbplates
 * and open the teventEjbplate in the editor.
 */
package it.polimi.meteocal.boundary;

import it.polimi.meteocal.control.EventEJB;
import it.polimi.meteocal.entity.Event;
import it.polimi.registration.business.security.entity.User;
import it.polimi.registration.gui.security.UserBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author jiasheng
 */
@ManagedBean
@Named(value = "eventBean")
@RequestScoped
public class EventManager {

    @EJB
    private EventEJB eventEjb;
    private Event event;
    private Event selectedEvent;
    private Date currentDate;
    private List<Event> eventlist;
    private static int passedParameter;
    private List<User> userlist;
    private final UserBean ub;

    public EventManager() {
        this.ub = new UserBean();
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
        if (eventlist == null) {
            eventlist = new ArrayList<>();
        }
        return eventlist;
    }

    public void setEventlist(List<Event> eventlist) {
        this.eventlist = eventlist;
    }

    public List<User> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public void loadEvent() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        passedParameter = Integer.parseInt((facesContext.getExternalContext().getRequestParameterMap().get("id")));
        selectedEvent = eventEjb.findEvent(passedParameter);
    }

    public Date getCurrentDate() {
        if (currentDate == null) {
            currentDate = new Date();
        }
        return currentDate;
    }

    public String create() {
        if (event.getDate() == null){
            event.setDate(new Date());
        }
        eventEjb.save(event);
        eventlist = eventEjb.findEvents();
        return ub.invite(event.getId());
    }

    @PostConstruct
    public void init() {
        eventlist = eventEjb.findEvents();
    }

    public String delete(int id) {
        try {
            eventEjb.deleteEvent(id);
            eventlist = eventEjb.findEvents();
            return "view";
        } catch (EJBException e) {
            System.out.println(e);
        }
        return "#";
    }

    public String editPage(int id) {
        return "edit?faces-redirect=true&id=" + id;
    }

    public String edit(Date date) {
        eventEjb.update(passedParameter, date);
        eventlist = eventEjb.findEvents();
        return "view";
    }

}
