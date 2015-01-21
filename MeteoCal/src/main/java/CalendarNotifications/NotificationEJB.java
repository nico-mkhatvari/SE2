/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarNotifications;

import it.polimi.meteocal.control.InvitationListEJB;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author terminator
 */
@Stateless
public class NotificationEJB {
    
    @PersistenceContext
    EntityManager em;
    
    public void deleteNotification(int id) {
        try{
        Notification n = em.find(Notification.class, id);
        em.remove(n);
        }
        catch(NullPointerException e){
            System.out.print("NON HO TROVATO LA NOTIFICA!!!!\n");
            System.err.println(e);
        }
    }
    
    public void createNotification(Notification notification) {
        try{
        em.persist(notification);
        }
        catch(NullPointerException e){
            System.out.print("OGGETTO DA CREARE E' NULLO!!!\n");
            System.err.println(e);
        }
    }
    
    public void updateNotification(Notification n) {
        
        try{
            em.merge(n);
        }
        catch(NullPointerException e){
            System.out.print("OGGETTO DA SALVARE E' NULLO!!!\n");
            System.err.println(e);
        }
    }

    
}
