package org.sideproject.chatter.receiver;

import org.sideproject.chatter.config.ActiveMQConfig;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
    @JmsListener(destination = ActiveMQConfig.QUEUE_NAME)
    public void handleMessage(String message) {//implicit message type conversion
        System.out.println("received: "+message);
    }
}