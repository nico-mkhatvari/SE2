/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.gui.security;

import it.polimi.registration.business.security.boundary.UserManager;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author miglie
 */
@Named(value = "userBean")
@RequestScoped
public class UserBean {

    @EJB
    UserManager um;

    public UserBean() {
    }

    public void setPublic() {
        um.setPublicUser();
    }

    public void setPrivate() {
        um.setPrivateUser();
    }

}
