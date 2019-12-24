package com.paulo.tarefas.api.config;

import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfig {


    @Value("${spring.activemq.broker-url}")
	private String brokerUrl;
    
    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;
    
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        if ( "".equals(user) ) {
            return new ActiveMQConnectionFactory(brokerUrl);
        }
        return new ActiveMQConnectionFactory(user, password, brokerUrl);
    }

//    @Bean
//    public JmsListenerContainerFactory jmsFactoryTopic(ConnectionFactory connectionFactory,
//                                                  DefaultJmsListenerContainerFactoryConfigurer configurer) {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        configurer.configure(factory, connectionFactory);
//        factory.setPubSubDomain(true);
//        return factory;
//    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(connectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplateTopic() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setPubSubDomain( true );
        return jmsTemplate;
    }
    
    @Bean 
    public Queue queue() {
    	return new ActiveMQQueue("standalone.email");
    }
}
