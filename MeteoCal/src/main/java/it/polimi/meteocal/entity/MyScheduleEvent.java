/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.entity;

import it.polimi.registration.business.security.entity.User;
import java.util.Date;
import java.util.List;
import org.primefaces.model.DefaultScheduleEvent;

/**
 *
 * @author jiasheng
 */
public class MyScheduleEvent extends DefaultScheduleEvent {

    private String id;
    private int eventId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean outdoor;
    private boolean privacy;
    private String city;
    private String address;
    private User organizer;
    private List<User> invitationlist;
    private List<User> participationlist;

    public MyScheduleEvent() {
    }

    public MyScheduleEvent(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public MyScheduleEvent(int eventId, String title, String description, Date startDate, Date endDate, boolean outdoor, boolean privacy, String city, String address, User organizer) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.outdoor = outdoor;
        this.privacy = privacy;
        this.city = city;
        this.address = address;
        this.organizer = organizer;
    }

    public MyScheduleEvent(int eventId, String title, String description, Date startDate, Date endDate, boolean outdoor, boolean privacy, String city, String address, User organizer, List<User> invitationlist, List<User> participationlist) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.outdoor = outdoor;
        this.privacy = privacy;
        this.city = city;
        this.address = address;
        this.organizer = organizer;
        this.invitationlist = invitationlist;
        this.participationlist = participationlist;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isOutdoor() {
        return outdoor;
    }

    public void setOutdoor(boolean outdoor) {
        this.outdoor = outdoor;
    }
    
    public boolean isPrivacy() {
        return privacy;
    }
    
    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }
    
    public boolean isPublic() {
        return privacy;
    }

    public void setPublic(boolean privacy) {
        this.privacy = privacy;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<User> getInvitationlist() {
        return invitationlist;
    }

    public void setInvitationlist(List<User> invitationlist) {
        this.invitationlist = invitationlist;
    }

    public List<User> getParticipationlist() {
        return participationlist;
    }

    public void setParticipationlist(List<User> participationlist) {
        this.participationlist = participationlist;
    }

}
