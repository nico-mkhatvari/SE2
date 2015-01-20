/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.boundary;

import it.polimi.registration.business.security.entity.Group;
import it.polimi.registration.business.security.entity.User;
import java.security.Principal;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author miglie
 */
@Stateless
public class UserManager {

    @PersistenceContext(unitName = "it.polimi_MeteoCal_war_1.0-SNAPSHOTPU")
    EntityManager em;

    @Inject
    Principal principal;

    public void save(User user) {
        user.setGroupname(Group.USERS);
        em.persist(user);
    }

    public void unregister() {
        em.remove(getLoggedUser());
    }

    public User getLoggedUser() {
        return em.find(User.class, principal.getName());
    }

    public User findUser(String email) {
        return em.find(User.class, email);
    }

    public List<User> findUsers() {
        TypedQuery<User> query = em.createNamedQuery("USERS.findAll", User.class);
        return query.getResultList();
    }

    public void setPublicUser() {
        User user = getLoggedUser();
        user.setPrivacy(false);
        em.merge(user);
    }

    public void setPrivateUser() {
        User user = getLoggedUser();
        user.setPrivacy(true);
        em.merge(user);
    }
}
