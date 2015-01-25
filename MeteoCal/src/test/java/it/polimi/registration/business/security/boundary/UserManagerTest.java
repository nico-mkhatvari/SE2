/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.registration.business.security.boundary;

import it.polimi.meteocal.control.EventsEJB;
import it.polimi.meteocal.entity.Events;
import it.polimi.registration.business.security.entity.Group;
import it.polimi.registration.business.security.entity.User;
import java.util.Date;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author miglie
 */
public class UserManagerTest {
    
    private UserManager um;
    
    @Before
    public void setUp() {
        um = new UserManager();
        um.em = mock(EntityManager.class);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void newUsersShouldBelongToUserGroupAndSavedOnce() {
        User newUser = new User();
        um.save(newUser);
        assertThat(newUser.getGroupname(), is(Group.USERS));
        verify(um.em,times(1)).persist(newUser);
    }
    
}
