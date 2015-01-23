/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.control;

import it.polimi.meteocal.entity.Events;
import java.util.Date;
import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author jiasheng
 */
@Singleton
public class EventsEJB extends AbstractFacade<Events> {
    @PersistenceContext(unitName = "it.polimi_MeteoCal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventsEJB() {
        super(Events.class);
    }

    public void save(Events event) {
        em.persist(event);
    }
    
    public Events findEvent(int id){
        return em.find(Events.class, id);
    }

    public List<Events> findEvents() {
        TypedQuery<Events> query = em.createNamedQuery("Events.findAll", Events.class);
        return query.getResultList();
    }

    public void deleteEvent(int id) {     
        Events e = em.find(Events.class, id);
        em.remove(e);        
    }    
    
    public void updateEvent(int id, String name, String description, Date startdate, Date enddate, Boolean outdoor, Boolean privacy, String city, String address){
        Events e = em.find(Events.class, id);
        e.setName(name);
        e.setDescription(description);
        e.setStartdate(startdate);
        e.setEnddate(enddate);
        e.setOutdoor(outdoor);
        e.setPrivacy(privacy);
        e.setCity(city);
        e.setAddress(address);
        em.merge(e);
    }
}
