/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.boundary;

import it.polimi.meteocal.entity.Event;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author jiasheng
 */
@Stateless
public class EventManager extends AbstractFacade<Event> {
    
    @PersistenceContext(unitName = "it.polimi_MeteoCal_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventManager() {
        super(Event.class);
    }

    public void save(Event event) {
        em.persist(event);
    }

    public List<Event> findEvents() {
        TypedQuery<Event> query = em.createNamedQuery("Event.findAll", Event.class);
        return query.getResultList();
    }

    public void deleteEvent(int id) {     
        
        Event e = em.find(Event.class, id);
        em.remove(e);
        
    }
}
