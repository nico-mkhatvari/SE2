/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.boundary;

import it.polimi.meteocal.control.EventsEJB;
import it.polimi.meteocal.entity.Events;
import it.polimi.registration.business.security.entity.User;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author miglie
 */
public class EventsEJBTest {
    
    EventsEJB eventEjb;
    
    @Before
    public void setUp() {
        eventEjb = new EventsEJB();
        eventEjb.em = mock(EntityManager.class);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void EventsEJBShouldBeInjected() {
        assertNotNull(eventEjb);
    }    
    
    @Test
    public void newEventsShouldBeSavedOnce() {
        User u = new User();
        u.setEmail("test@email.com");
        Events e = new Events("pizza", "pizza  party", new Date(), new Date(), true, true, "Milano", "Duomo", u);
        eventEjb.create(e);
        verify(eventEjb.em,times(1)).persist(e);
    }
    
    @Test
    public void OrganizerShouldExist() {
        User organizer = new User();
        organizer.setName("Pino");
        organizer.setEmail("pino@email.com");
        organizer.setPassword("123456");
        organizer.setPrivacy(false);
        Events match = new Events("calcio", "partitella", new Date(), new Date(), true, true, "Milano", "San Siro", organizer);
        match.setId(1);
        eventEjb.save(match);
        assertNotNull(match);
        assertEquals(match.getOrganizer().getEmail(), "pino@email.com");
    }
}
