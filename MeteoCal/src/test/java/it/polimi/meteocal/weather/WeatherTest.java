/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.weather;

import it.polimi.meteocal.entity.Events;
import it.polimi.registration.business.security.entity.User;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jiasheng
 */
public class WeatherTest {
    
    public WeatherTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testGetSingleWeather() {
        System.out.println("getSingleWeather");
        Events e = new Events(new Date(), new Date(), true, true, "Milano", new User());
        Weather instance = new Weather();
        WeatherData result = instance.getSingleWeather(e);

        assertNotNull(result);
    }
    
    @Test (expected = NullPointerException.class)
    public void TestGetNullSingleWeather() {
        System.out.println("getSingleWeather");
        Events e = new Events(null, null, true, true, null, new User());
        Weather instance = new Weather();
        WeatherData result = instance.getSingleWeather(e);
    }
    
}
