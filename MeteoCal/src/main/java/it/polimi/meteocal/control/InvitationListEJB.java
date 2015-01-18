/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.control;

import it.polimi.meteocal.entity.Events;
import it.polimi.meteocal.entity.InvitationList;
import it.polimi.meteocal.entity.InvitationListPK;
import it.polimi.registration.business.security.entity.User;
import java.security.Principal;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jiasheng
 */
@Stateless
public class InvitationListEJB extends AbstractFacade<InvitationList> {

    @PersistenceContext(unitName = "it.polimi_MeteoCal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Inject
    Principal principal;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvitationListEJB() {
        super(InvitationList.class);
    }

    public List<InvitationList> findInvitationList(int eventid) {
        List<InvitationList> list = em.createNamedQuery("findAllInvitationListWithEventId").setParameter("eventid", eventid).getResultList();
        return list;
    }

    public List<InvitationList> findParticipationList(int eventid) {
        List<InvitationList> list = em.createNamedQuery("findAllParticipatingListWithEventId").setParameter("eventid", eventid).setParameter("participate", true).getResultList();
        return list;
    }
    
    public List<InvitationList> findInvitationListByEmail(String usermail) {
        List<InvitationList> list = em.createNamedQuery("findAllInvitationListWithUserEmail").setParameter("user", usermail).getResultList();
        return list;
    }
    
    public List<InvitationList> findParticipatingListByEmail(String usermail) {
        List<InvitationList> list = em.createNamedQuery("findAllParticipatingListWithUserEmail").setParameter("user", usermail).setParameter("participate", true).getResultList();
        return list;
    }
    
    public List<InvitationList> findUserByEventid(int eventid, String usermail) {
        List<InvitationList> list = em.createNamedQuery("findUserWithEventId").setParameter("eventid", eventid).setParameter("user", usermail).getResultList();
        return list;
    }

    public void save(Events event, List<User> invitedlist) {
        User eventOrganizer = em.find(User.class, principal.getName()); //logged user
        if (findUserByEventid(event.getId(), eventOrganizer.getEmail()) == null) { //if it's a new event
            InvitationList myInvitationlist = new InvitationList(eventOrganizer, event);
            myInvitationlist.setParticipate(true);
            InvitationListPK mypk = new InvitationListPK(eventOrganizer.getEmail(), event.getId());
            myInvitationlist.setInvitationListPK(mypk);
            em.persist(myInvitationlist);
        }
        if (invitedlist == null) {
        } else {
            for (int i = 0; i < invitedlist.size(); i++) {
                User invited = invitedlist.get(i);
                List<InvitationList> tempList = findUserByEventid(event.getId(), invited.getEmail());
                if (tempList.isEmpty()) { //if user is never been invited
                    InvitationList invitationlist = new InvitationList();
                    invitationlist.setUser1(invited);
                    invitationlist.setEvents(event);
                    invitationlist.setParticipate(false); //event organizer is the only participant
                    InvitationListPK pk = new InvitationListPK(invited.getEmail(), event.getId());
                    invitationlist.setInvitationListPK(pk);
                    em.persist(invitationlist);
                } else {
                    tempList.get(0).setParticipate(false); //get(0) because a user can be invited only once
                }
            }
        }
    }

    public void remove(int eventid) {
        List<InvitationList> invitationlist = findInvitationList(eventid);
        for (int i = 0; i < invitationlist.size(); i++) {
            em.remove(invitationlist.get(i));
        }
    }
}
