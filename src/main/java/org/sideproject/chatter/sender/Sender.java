package org.sideproject.chatter.sender;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sideproject.chatter.config.ActiveMQConfig;
import org.sideproject.chatter.message.ChatterMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	protected Logger log = LogManager.getLogger(this.getClass());
	
	@Autowired
    private ConnectionFactory connectionFactory;
    private JmsTemplate jmsTemplate;

    @PostConstruct
    public void init() {
        this.jmsTemplate = new JmsTemplate(connectionFactory);
        this.jmsTemplate.setPubSubDomain(true);
    }

    public void sendMessage(String message) {
    	log.info("sending: " + "<" + message + ">");
        jmsTemplate.convertAndSend(ActiveMQConfig.DESTINATION_NAME, message);
    }
    
    public void sendMessage(ChatterMessage message) {
    	log.info("sending: " + "<" + message + ">");
        jmsTemplate.convertAndSend(message.getDes_Name(), message.getContent());
    }

//    @Autowired
//    private JmsTemplate jmsTemplate;
//
//    public void send(Message message) {
//        log.info("sending with convertAndSend() <" + message + ">");
//        jmsTemplate.convertAndSend(message);
//    }
}
