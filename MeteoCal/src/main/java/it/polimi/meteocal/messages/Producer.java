package it.polimi.meteocal.messages;

/*
 http://www.hascode.com/2011/06/message-driven-beans-in-java-ee-6/
 https://weblogs.java.net/blog/kalali/archive/2006/05/step_by_step_to.html
 https://today.java.net/pub/a/today/2008/01/22/jms-messaging-using-glassfish.html#configure_resource
 https://www.packtpub.com/books/content/setting-glassfish-jms-and-working-message-queues
 */
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.JMSSessionMode;
import javax.jms.TextMessage;

@Named("producersms")
@RequestScoped
public class Producer{
    
    @Inject
    @JMSConnectionFactory("jms/tConnectionFactory")
    @JMSSessionMode(JMSContext.AUTO_ACKNOWLEDGE)
    private JMSContext context;
    @Resource(lookup = "jms/tQueue")
    private Destination queue;
    private JMSProducer producer;
    private TextMessage message;

    public Producer() {

    }

    @PostConstruct
    public void init() {
        producer = context.createProducer();
        message = context.createTextMessage("Today is: " + new Date());
    }

    public void buttonAction(ActionEvent actionEvent) {
        addMessage("Welcome to Primefaces!!");
        sendMessage();
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage( summary);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void sendMessage() {

        producer.send(queue, message);

    }

    public JMSContext getContext() {
        return context;
    }

    public void setContext(JMSContext context) {
        this.context = context;
    }

    public Destination getQueue() {
        return queue;
    }

    public void setQueue(Destination queue) {
        this.queue = queue;
    }

    public JMSProducer getProducer() {
        return producer;
    }

    public void setProducer(JMSProducer producer) {
        this.producer = producer;
    }

    public TextMessage getMessage() {
        return message;
    }

    public void setMessage(TextMessage message) {
        this.message = message;
    }
}
