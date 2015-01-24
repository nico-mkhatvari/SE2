package it.polimi.meteocal.boundary;

import it.polimi.meteocal.control.EventsEJB;
import it.polimi.meteocal.control.InvitationListEJB;
import it.polimi.meteocal.entity.Events;
import it.polimi.meteocal.entity.InvitationList;
import it.polimi.meteocal.entity.MyScheduleEvent;
import it.polimi.registration.business.security.boundary.UserManager;
import it.polimi.registration.business.security.entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@ManagedBean
@Named(value = "calendarBean")
@ViewScoped
public class CalendarManager implements Serializable {

    private ScheduleModel model;
    private MyScheduleEvent scheduleEvent = new MyScheduleEvent();
    private Date today = new Date();
    private Events event;
    private User loggedUser;
    private User calendarOwner;
    @EJB
    private EventsEJB eventsEjb;
    @EJB
    private UserManager um;
    @EJB
    private InvitationListEJB invitationListEJB;
    private boolean disableForecast;

    @PostConstruct
    public void init() {
        model = new DefaultScheduleModel();
        loggedUser = um.getLoggedUser();
        disableForecast = true; // disables weather info tab for new events

        //gets the calendar's owner of the research by email
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext.getExternalContext().getRequestParameterMap().get("email") == null) {
            calendarOwner = loggedUser;
        } else {
            String searchedUser = facesContext.getExternalContext().getRequestParameterMap().get("email");
            if (um.findUser(searchedUser) == null) {//if searched user doesn't exist  
                calendarOwner = loggedUser;
            } else {
                calendarOwner = um.findUser(searchedUser);
            }
        }

        //gets the list of the events where the owner is a participant
        List<InvitationList> myInvitationlist = invitationListEJB.findParticipatingListByEmail(calendarOwner.getEmail());
        List<Events> myEventlist = new ArrayList<>();
        for (int i = 0; i < myInvitationlist.size(); i++) {
            InvitationList tempList = myInvitationlist.get(i);

            //if it's public adds it to the event list
            if (tempList.getEvents().getPrivacy() == false || loggedUser.equals(calendarOwner)) {
                myEventlist.add(tempList.getEvents());
            }
        }

        //stores the whole data of this event in the POJO MyScheduleEvent 
        for (int i = 0; i < myEventlist.size(); i++) {
            Events tempEvent = myEventlist.get(i);
            int eventId = tempEvent.getId();
            User eventOrganizer = tempEvent.getOrganizer();
            String eventName = tempEvent.getName();
            String eventDescription = tempEvent.getDescription();
            Date eventStartdate = tempEvent.getStartdate();
            Date eventEnddate = tempEvent.getEnddate();
            Boolean eventOutdoor = tempEvent.getOutdoor();
            Boolean eventPrivacy = tempEvent.getPrivacy();
            String eventCity = tempEvent.getCity();
            String eventAddress = tempEvent.getAddress();

            //retrieves the information about the invitation list for this event
            List<InvitationList> eventInvitationlist = invitationListEJB.findInvitationList(eventId);
            List<User> eventInvitedList = new ArrayList<>();
            for (int j = 0; j < eventInvitationlist.size(); j++) {
                User eventInvited = eventInvitationlist.get(j).getUser1();
                eventInvitedList.add(eventInvited);
            }

            //retrieves the information about the participation list for this event
            List<InvitationList> eventParticipationlist = invitationListEJB.findParticipationList(eventId);
            List<User> eventParticipatingList = new ArrayList<>();
            for (int j = 0; j < eventParticipationlist.size(); j++) {
                User eventParticipating = eventParticipationlist.get(j).getUser1();
                eventParticipatingList.add(eventParticipating);
            }

            //saves this data and store it in the data structure
            model.addEvent(new MyScheduleEvent(eventId, eventName, eventDescription, eventStartdate, eventEnddate,
                    eventOutdoor, eventPrivacy, eventCity, eventAddress, eventOrganizer, eventInvitedList, eventParticipatingList));
        }
    }

// Getters and Setters
////////////////////////////////////////////////////////////////////////////////////////
    public ScheduleModel getModel() {
        return model;
    }

    public void setModel(ScheduleModel model) {
        this.model = model;
    }

    public ScheduleEvent getScheduleEvent() {
        return scheduleEvent;
    }

    public void setScheduleEvent(MyScheduleEvent scheduleEvent) {
        this.scheduleEvent = scheduleEvent;
    }

    public Date getToday() {
        return today;
    }

    public Events getEvents() {
        return event;
    }

    public void setEvents(Events event) {
        this.event = event;
    }

    public User getCalendarOwner() {
        return calendarOwner;
    }

    public void setCalendarOwner(User calendarOwner) {
        this.calendarOwner = calendarOwner;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean isDisableForecast() {
        return disableForecast;
    }

////////////////////////////////////////////////////////////////////////////////////////
    public void addEvent() {
        User eventOrganizer = loggedUser;
        int eventId = scheduleEvent.getEventId();
        String eventName = scheduleEvent.getTitle();
        String eventDescription = scheduleEvent.getDescription();
        Date eventStartdate = scheduleEvent.getStartDate();
        Date eventEnddate = scheduleEvent.getEndDate();
        Boolean eventOutdoor = scheduleEvent.isOutdoor();
        Boolean eventPrivacy = scheduleEvent.isPublic();
        String eventCity = scheduleEvent.getCity();
        String eventAddress = scheduleEvent.getAddress();
        List<User> eventInvitedList = scheduleEvent.getParticipationlist(); //get the selection

        //if it's an existing event, delete it
        if (eventsEjb.find(eventId) != null) {
            eventsEjb.deleteEvent(eventId);
            model.deleteEvent(scheduleEvent);
        }
        
        //saves a new event
        event = new Events(eventName, eventDescription, eventStartdate, eventEnddate,
                eventOutdoor, eventPrivacy, eventCity, eventAddress, eventOrganizer);
        eventsEjb.create(event);
        invitationListEJB.save(event, eventInvitedList);
        model.addEvent(scheduleEvent);

        scheduleEvent = new MyScheduleEvent();//reset dialog form
        init();
    }

    public void deleteEvent() {
        if (scheduleEvent.getId() == null) {
        } else {
            int eventid = scheduleEvent.getEventId();
            eventsEjb.deleteEvent(eventid); // invitationlist is deleted on cascade
            model.deleteEvent(scheduleEvent);
        }
        scheduleEvent = new MyScheduleEvent();//reset dialog form
        init();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        scheduleEvent = (MyScheduleEvent) selectEvent.getObject();
        if(((MyScheduleEvent)selectEvent.getObject()).isOutdoor() == true){  //forecast only for outdoor event
            disableForecast = false;
        }
    }

    public void onDateSelect(SelectEvent selectEvent) {
        scheduleEvent = new MyScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        disableForecast = true;    
    }

    public boolean isNotOrganizer() {
        if (scheduleEvent.getOrganizer() == null) {
            return false;
        }
        return !(scheduleEvent.getOrganizer().equals(loggedUser));
    }

    public boolean isNotOwner() {
        return !(calendarOwner.equals(loggedUser));
    }

    public String search(String email) {
        if (um.findUser(email) == null) {
            return "usernotfound?faces-redirect=true&email=" + email;
        }
        if (um.findUser(email).getPrivacy() == true){
            return "privatecalendar?faces-redirect=true&email=" + email;
        }
        return "calendar?faces-redirect=true&email=" + email;
    }
}
