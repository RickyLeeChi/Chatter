package org.sideproject.chatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;

import org.sideproject.chatter.chatroom.ChatRoom;
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
	
	private static AtomicInteger roomIdSeed = new AtomicInteger(1000);
	
	List<Thread> chatrooms = new ArrayList<Thread>();

	
    public static void main( String[] args ) {
    	init(); 	

    	Application m = applicationContext.getBean(Application.class);
    	
    	m.run();
 
    }
    
    private void addMessageListener(List<String> names) {  	
    	for(String name : names) {
    		int id = roomIdSeed.incrementAndGet();
    		SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
    		endpoint.setMessageListener(new SimpleMessageListener());
    		endpoint.setDestination(name);
    		endpoint.setId(String.valueOf(id));
    		
    		jmslistenerEntry.registerListenerContainer(endpoint, jmsListenerContainerFactory);
    		
    		chatrooms.add(new Thread(new ChatRoom(name, id), name));
    	}
    	
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
    	
    	System.out.println("Please enter chatter names : ");
    	
    	System.out.print("# ");
    	
    	List<String> chatterNames = new ArrayList<String>();
    	
    	boolean running = true;
        
        while(running){
        	String name = userInput.nextLine();
         	
        	if(name.equalsIgnoreCase("exit")) {
                userInput.close();
                System.out.println("Thank you!!");
                running = false;
        	}
        	
        	else {
            	chatterNames.add(name);
        	}
        	
        	System.out.print("# ");
        }
        
        //
    	addMessageListener(chatterNames);
    	
    	//Open chat rooms
    	for(Thread t : chatrooms) {
    		t.start();
    	}
    	
    	
    	List<ChatterMessage> messages = new ArrayList<ChatterMessage>();
    	
    	for(String name : chatterNames) {
    		ChatterMessage message = new ChatterMessage(name, new Date(), name);
    		messages.add(message);
    	}
    	
//    	for (MessageListenerContainer listenerContainer :jmslistenerEntry.getListenerContainers()) {
//    		DefaultMessageListenerContainer container = (DefaultMessageListenerContainer) listenerContainer;
//    		
//    		for(ChatterMessage message : messages) {
//    			if(message.getDes_Name().equalsIgnoreCase(container.getDestinationName())) {
//    				sender.sendMessage(message);
//    			}
//    		}
//    	}
    	
    	for(ChatterMessage message : messages) {
    		sender.sendMessage(message);
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

