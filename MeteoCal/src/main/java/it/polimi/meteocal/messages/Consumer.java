/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.messages;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author terminator
 */
public class Consumer implements MessageListener{
    
    @Inject
    @JMSConnectionFactory("jms/tConnectionFactory")
    private JMSContext context;
    @Resource(lookup="jms/tQueue")
    private Destination queue;
    
    private JMSConsumer consumer;
    private TextMessage msg;
    
    public Consumer(){
        
    }
    
    @PostConstruct
    public void init(){
        consumer = context.createConsumer(queue);
        msg = context.createTextMessage();
    }

    @Override
    public void onMessage(Message message) {
        
        try {
            msg = (TextMessage)message.getBody(TextMessage.class);
        } catch (JMSException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            String mymsg = msg.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
}
