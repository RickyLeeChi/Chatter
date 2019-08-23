package org.sideproject.chatter;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;

import org.sideproject.chatter.message.ChatterMessage;
import org.sideproject.chatter.receiver.SimpleMessageListener;
import org.sideproject.chatter.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class Application 
{		
	public static AnnotationConfigApplicationContext applicationContext;
	
	
	@Autowired
	private Sender sender;
	
	@Autowired
	private JmsListenerEndpointRegistry jmslistenerEntry;
	
//	@Autowired
//	private MessageListenerContainer messageListener;
	
//	@Autowired
//	private DefaultMessageListenerContainer dmlc;
	
	@Autowired
	JmsListenerContainerFactory<?> jmsListenerContainerFactory;
	
    public static void main( String[] args ) {
    	init(); 	

    	Application m = applicationContext.getBean(Application.class);
    	
    	m.run();
 
    }
    
    private void addMessageListener() {
    	
		SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
		endpoint.setMessageListener(new SimpleMessageListener());
		endpoint.setDestination("RTEST1");
		endpoint.setId("123");

		jmslistenerEntry.registerListenerContainer(endpoint, jmsListenerContainerFactory);

		SimpleJmsListenerEndpoint endpoint2 = new SimpleJmsListenerEndpoint();
		endpoint2.setMessageListener(new SimpleMessageListener());
		endpoint2.setDestination("RTEST2");
		endpoint2.setId("456");

		jmslistenerEntry.registerListenerContainer(endpoint2, jmsListenerContainerFactory);
		
		jmslistenerEntry.start();
    }

    private void run() {	
//    	Runnable runnable = () -> {
//            System.out.println("Inside : " + Thread.currentThread().getName());
//        };
//        
//        for(int i = 0 ; i < 5 ; i++) {
//        	System.out.println("Creating Thread..." + "<" + i + ">");
//            Thread thread = new Thread(runnable);
//
//            System.out.println("Starting Thread..." + "<" + i + ">");
//            thread.start();
//        }

//
//    	dmlc.setCacheLevel(DefaultMessageListenerContainer.CACHE_CONNECTION);
    	
    	Scanner userInput = new Scanner(System.in);
    	
    	System.out.println("Please enter your user name : ");
    	
    	System.out.print("# ");
    	
    	boolean running = true;
        
        while(running){
        	String userName = userInput.nextLine();
         	
        	if(userName.equalsIgnoreCase("exit")) {
                userInput.close();
                System.out.println("Bye ~ Bye!!");
        		System.exit(0);
        	}
        	
        	System.out.print("# ");
        }
        
    	addMessageListener();
    	
    	ChatterMessage message = new ChatterMessage("Rtest1", new Date(), "RTEST1");
    	
    	ChatterMessage message2 = new ChatterMessage("Rtest2", new Date(), "RTEST2");
    	
    	for (MessageListenerContainer listenerContainer :jmslistenerEntry.getListenerContainers()) {
    		DefaultMessageListenerContainer container = (DefaultMessageListenerContainer) listenerContainer;
    		
    		if(container.getDestinationName().equalsIgnoreCase("RTEST1")) {
    			sender.sendMessage(message);
    		}
    		
    		if(container.getDestinationName().equalsIgnoreCase("RTEST2")) {
    			sender.sendMessage(message2);
    		}
    	}
    	
    	
//    	ChatterMessage message3 = new ChatterMessage("Rtest3", new Date(), "RTEST3");
//    	  	
//    	dmlc.setDestinationName("RTEST3");
//    	
//    	sender.sendMessage(message3);
    	
    	System.out.println("-- shutting down listener container --");
    	for (MessageListenerContainer listenerContainer :jmslistenerEntry.getListenerContainers()) {
    		DefaultMessageListenerContainer container = (DefaultMessageListenerContainer) listenerContainer;
    		container.shutdown();
    	}
    	
//    	dmlc.shutdown();
    	
//    	DefaultMessageListenerContainer container = applicationContext.getBean(DefaultMessageListenerContainer.class);
//    	container.shutdown();
	}
    
	private static void init() {
    	applicationContext = new AnnotationConfigApplicationContext();
    	applicationContext.register(Application.class);
    	applicationContext.scan("org.sideproject.chatter");
    	applicationContext.refresh();  
 	}
}

