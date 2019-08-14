package org.sideproject.chatter;

import org.sideproject.chatter.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
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
	
	@Autowired
	private DefaultMessageListenerContainer dmlc;
	
    public static void main( String[] args ) {
    	init(); 	

    	Application m = applicationContext.getBean(Application.class);
    	
    	m.run();
 
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
    	sender.sendMessage("Ricky test1");
    	sender.sendMessage("Ricky test2");
    	
    	System.out.println("-- shutting down listener container --");
//    	for (MessageListenerContainer listenerContainer :jmslistenerEntry.getListenerContainers()) {
//    		DefaultMessageListenerContainer container = (DefaultMessageListenerContainer) listenerContainer;
//    		container.shutdown();
//    	}
    	
    	dmlc.shutdown();
    	
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

