/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.entity;

import java.util.Date;

/**
 *
 * @author jiasheng
 */
public class MyNotification {
    
    private int notificationId;
    private int eventId;
    private String userEmail;
    private String eventName;
    private String eventDescription;
    private Date eventStartdate;
    private Date eventEnddate;
    private String eventCity;
    private String eventAddress;
    private String message;
    private Date suggestedDay;

    public MyNotification(int notificationId, int eventId, String userEmail, String eventName, String eventDescription, Date eventStartdate, Date eventEnddate, String eventCity, String eventAddress, String message) {
        this.notificationId = notificationId;
        this.eventId = eventId;
        this.userEmail = userEmail;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventStartdate = eventStartdate;
        this.eventEnddate = eventEnddate;
        this.eventCity = eventCity;
        this.eventAddress = eventAddress;
        this.message = message;
    }

    public MyNotification(int notificationId, int eventId, String userEmail, String eventName, String eventDescription, Date eventStartdate, Date eventEnddate, String eventCity, String eventAddress, String message, Date suggestedDay) {
        this.notificationId = notificationId;
        this.eventId = eventId;
        this.userEmail = userEmail;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventStartdate = eventStartdate;
        this.eventEnddate = eventEnddate;
        this.eventCity = eventCity;
        this.eventAddress = eventAddress;
        this.message = message;
        this.suggestedDay = suggestedDay;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getEventStartdate() {
        return eventStartdate;
    }

    public void setEventStartdate(Date eventStartdate) {
        this.eventStartdate = eventStartdate;
    }

    public Date getEventEnddate() {
        return eventEnddate;
    }

    public void setEventEnddate(Date eventEnddate) {
        this.eventEnddate = eventEnddate;
    }

    public String getEventCity() {
        return eventCity;
    }

    public void setEventCity(String eventCity) {
        this.eventCity = eventCity;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSuggestedDay() {
        return suggestedDay;
    }

    public void setSuggestedDay(Date suggestedDay) {
        this.suggestedDay = suggestedDay;
    }


    
}
