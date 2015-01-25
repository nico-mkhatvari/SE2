package it.polimi.registration.business.security.boundary;

import it.polimi.registration.business.security.entity.Group;
import it.polimi.registration.business.security.entity.User;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author jiasheng
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
