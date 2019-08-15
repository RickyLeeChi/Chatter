package org.sideproject.chatter.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.sideproject.chatter.receiver.SimpleMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

import javax.jms.ConnectionFactory;

@Configuration
@ComponentScan
@EnableJms
@PropertySource("classpath:application.properties")
public class ActiveMQConfig {
	
	public static final String DESTINATION_NAME = "Mytest.destination";
	
	@Value("${activemq.BrokerURL}")
    private String brokerURL;
	
//	@Value("${activemq.QUEUE_NAME}")
//    private String QUEUE_NAME;

	@Value("${activemq.Concurrency}")
    private String concurrentcy;

    @Bean
    public ConnectionFactory connectionFactory() {
    	ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(brokerURL);
        return connectionFactory;
    }

//    @Bean
//    public JmsListenerContainerFactory<?> jmsListenerContainerFactory() {
//        DefaultJmsListenerContainerFactory factory =
//                new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory());
//        //core poll size=4 threads and max poll size 8 threads
////        factory.setConcurrency("4-8");
//        factory.setConcurrency(concurrentcy);
//        factory.setPubSubDomain(true);
//        return factory;
//    }
    
//    @Bean
//    public MessageListenerContainer listenerContainer() {
//        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory());
//        container.setDestinationName(QUEUE_NAME);
//        container.setMessageListener(new SimpleMessageListener());
//        return container;
//    }
    
    @Bean
    public DefaultJmsListenerContainerFactory orderDefaultJmsListenerContainerFactory() {
      DefaultJmsListenerContainerFactory factory =
          new DefaultJmsListenerContainerFactory();
      factory.setConnectionFactory(connectionFactory());
      factory.setConcurrency(concurrentcy);
//      factory.setPubSubDomain(true);
      
      return factory;
    }
    
    @Bean
    public DefaultMessageListenerContainer orderMessageListenerContainer() {
      SimpleJmsListenerEndpoint endpoint =
          new SimpleJmsListenerEndpoint();
      endpoint.setMessageListener(new SimpleMessageListener());
      endpoint.setDestination(DESTINATION_NAME);

      return orderDefaultJmsListenerContainerFactory()
          .createListenerContainer(endpoint);
    }

}