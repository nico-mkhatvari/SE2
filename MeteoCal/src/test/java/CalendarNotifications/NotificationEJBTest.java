/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarNotifications;

import it.polimi.meteocal.entity.Events;
import it.polimi.registration.business.security.entity.User;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author jiasheng
 */
public class NotificationEJBTest {
    
    private NotificationEJB notEjb;
    
    public NotificationEJBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        notEjb = new NotificationEJB();
        notEjb.em = Mockito.mock(EntityManager.class, Mockito.RETURNS_MOCKS);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createNotification method, of class NotificationEJB.
     */
    @Test
    public void testCreateNotificationOnlyOnce() {
        Notification n = new Notification();
        n.setId(1);
        n.setUseremail(new User());
        n.setViewed(false);
        n.setEventid(new Events());
        notEjb.createNotification(n);
        verify(notEjb.em,times(1)).persist(n);
    }

}
