package org.sideproject.chatter.receiver;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SimpleMessageListener implements MessageListener {
	
	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				String text = ((TextMessage) message).getText();
				System.out.println("Simple listener received: " + text);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
