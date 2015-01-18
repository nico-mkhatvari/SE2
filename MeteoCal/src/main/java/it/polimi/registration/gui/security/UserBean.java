/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.gui.security;

import it.polimi.meteocal.control.EventEJB;
import it.polimi.meteocal.entity.Event;
import it.polimi.registration.business.security.boundary.UserManager;
import it.polimi.registration.business.security.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author miglie
 */
@Named(value = "userBean")
@RequestScoped
public class UserBean {

    @EJB
    UserManager um;
    private User foundUser;
    private int passedParameter;
    private Event selectedEvent;
    private List<User> listUsers;
    private List<User> selectedUsers;
    private List<String> selectedEmails;
    private EventEJB eventEjb;

    public UserBean() {
    }

    public User getFoundUser() {
        if (foundUser == null) {
            foundUser = new User();
        }
        return foundUser;
    }

    public void setFoundUser(User foundUser) {
        this.foundUser = foundUser;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public int getPassedParameter() {
        return passedParameter;
    }

    public void setPassedParameter(int passedParameter) {
        this.passedParameter = passedParameter;
    }

    public List<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    public List<User> getSelectedUsers() {
        /*if (selectedUsers == null)
            selectedUsers = new ArrayList<>();*/
        return selectedUsers;
    }

    public void setSelectedUsers(List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public List<String> getSelectedEmails(List<User> selectedUsers) {
        if(selectedEmails == null)
            selectedEmails = new ArrayList<>();       
        for (int i = 0; i < this.selectedUsers.size(); i++) {
            String email = this.selectedUsers.get(i).getEmail();
            selectedEmails.add(email);
        }
        return selectedEmails;
    }

    public String getName() {
        return um.getLoggedUser().getName();
    }

    public void loadUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String searchedUser = (facesContext.getExternalContext().getRequestParameterMap().get("email"));
        foundUser = um.findUser(searchedUser);
    }

    public void loadUsers() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        passedParameter = Integer.parseInt((facesContext.getExternalContext().getRequestParameterMap().get("id")));
        selectedEvent = eventEjb.findEvent(passedParameter);
        //String allUsers = (facesContext.getExternalContext().getRequestParameterMap().get("email"));
        //listUsers = um.addUsers(passedParameter, allUsers);        
    }

    @PostConstruct
    public void init() {
        listUsers = um.findUsers();
    }

    public String search(String email) {
        return "result?faces-redirect=true&email=" + email;
    }

    public String invite(int id) {
        return "invitationlist?faces-redirect=true&id=" + id;
    }

    public String test(List<String> selectedEmails) {
        return "result?faces-redirect=true&selectedEmails=" + selectedEmails;
    }
}
