/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.boundary;

import it.polimi.registration.business.security.boundary.UserManager;
import it.polimi.registration.business.security.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author jiasheng
 */
@Named (value="invitationBean")
@RequestScoped
public class InvitationManager {
    
    @EJB
    private UserManager um;
    private List<User> invitationlist;

    public InvitationManager() {
    }

    public List<User> getInvitationlist() {
        if (invitationlist == null)
            invitationlist = new ArrayList<>();
        User loggedUser = um.getLoggedUser();
        invitationlist = um.findUsers();
        invitationlist.remove(loggedUser);
        return invitationlist;
    }

    public void setInvitationlist(List<User> invitationlist) {
        this.invitationlist = invitationlist;
    }

}
