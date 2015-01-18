package it.polimi.meteocal.messages;



import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author terminator
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/tQueue"),
    @ActivationConfigProperty(propertyName = "ackwnoledgeMode", propertyValue="Auto-acknowledge")
    
})
public class TMDB implements MessageListener {

    @Resource
    private MessageDrivenContext mdc;

    public TMDB() {
    }

    @Override
    public void onMessage(Message message) {
        
        TextMessage msg = null;
        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                System.out.println("A Message received in TMDB: "
                        + msg.getText());
                //message.acknowledge();
            } else {
                System.out.println("Message of wrong type: "
                        + message.getClass().getName());
                message.acknowledge();

            }
        } catch (JMSException e) {
            e.printStackTrace();
            mdc.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }

}
