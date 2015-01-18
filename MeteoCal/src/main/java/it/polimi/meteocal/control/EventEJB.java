/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.control;

import it.polimi.meteocal.entity.Event;
<<<<<<< HEAD
=======
import java.util.Date;
>>>>>>> 868fb28ad4de413eb37772e49239dc410cf43e9b
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

<<<<<<< HEAD

=======
/**
 *
 * @author jiasheng
 */
>>>>>>> 868fb28ad4de413eb37772e49239dc410cf43e9b
@Stateless
public class EventEJB extends AbstractFacade<Event> {
    
    @PersistenceContext(unitName = "it.polimi_MeteoCal_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
<<<<<<< HEAD
=======
    
    @Override
>>>>>>> 868fb28ad4de413eb37772e49239dc410cf43e9b
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventEJB() {
        super(Event.class);
    }

    public void save(Event event) {
        em.persist(event);
    }
<<<<<<< HEAD
=======
    
    public Event findEvent(int id){
        return em.find(Event.class, id);
    }
>>>>>>> 868fb28ad4de413eb37772e49239dc410cf43e9b

    public List<Event> findEvents() {
        TypedQuery<Event> query = em.createNamedQuery("Event.findAll", Event.class);
        return query.getResultList();
    }

    public void deleteEvent(int id) {     
<<<<<<< HEAD
        
        Event e = em.find(Event.class, id);
        em.remove(e);
        
=======
        Event e = em.find(Event.class, id);
        em.remove(e);        
    }
    
    public void update(int id, Date date){
    	Event e = em.find(Event.class, id);
    	e.setDate(date);
    	em.merge(e);
>>>>>>> 868fb28ad4de413eb37772e49239dc410cf43e9b
    }
}
