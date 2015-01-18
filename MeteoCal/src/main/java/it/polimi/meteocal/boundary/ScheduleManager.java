package it.polimi.meteocal.boundary;

import it.polimi.meteocal.control.EventEJB;
import it.polimi.meteocal.entity.Event;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@ManagedBean
@Named(value = "scheduleBean")
@ViewScoped
public class ScheduleManager implements Serializable {

    private ScheduleModel model;
    private ScheduleEvent scheduleEvent = new DefaultScheduleEvent();
    private List<Event> eventlist;
    private Event event;
    @EJB
    private EventEJB eventEjb;

    @PostConstruct
    public void init() {
        model = new DefaultScheduleModel();
        eventlist = eventEjb.findEvents();
        for (int i = 0; i < eventlist.size(); i++) {
            String eventName = "" + eventlist.get(i).getId();
            Date eventDate = eventlist.get(i).getDate();
            Calendar c = Calendar.getInstance(); // date before 
            c.setTime(eventDate);
            c.add(Calendar.DATE, -1);
            eventDate = c.getTime();  //
            model.addEvent(new DefaultScheduleEvent(eventName, eventDate, eventDate));
        }
    }

    public ScheduleModel getModel() {
        return model;
    }

    public void setModel(ScheduleModel model) {
        this.model = model;
    }

    public ScheduleEvent getScheduleEvent() {
        return scheduleEvent;
    }

    public void setScheduleEvent(ScheduleEvent scheduleEvent) {
        this.scheduleEvent = scheduleEvent;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void addEvent() {
        Date eventDate = (Date) scheduleEvent.getStartDate();
        Calendar c = Calendar.getInstance(); // date after 
        c.setTime(eventDate);
        c.add(Calendar.DATE, 1);
        eventDate = c.getTime();  //
        if (scheduleEvent.getId() == null) {
            event = new Event();
            event.setDate(eventDate);
            eventEjb.create(event);
            model.addEvent(scheduleEvent);
        } else { //if id exists, modify
            int eventId = Integer.parseInt(scheduleEvent.getTitle());
            event = eventEjb.find(eventId);
            eventEjb.update(eventId, eventDate);
            model.updateEvent(scheduleEvent);
        }
        scheduleEvent = new DefaultScheduleEvent();//reset dialog form
        init();
    }

    public void deleteEvent() {
        if (scheduleEvent.getId() == null) {
        } else {
            int eventId = Integer.parseInt(scheduleEvent.getTitle());
            eventEjb.deleteEvent(eventId);
            model.updateEvent(scheduleEvent);
        }
        scheduleEvent = new DefaultScheduleEvent();//reset dialog form
        init();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        scheduleEvent = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        scheduleEvent = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }
}
