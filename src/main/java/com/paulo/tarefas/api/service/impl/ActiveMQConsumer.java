package com.paulo.tarefas.api.service.impl;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQConsumer {

	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	Queue queue;
	
	public void sendMessage(String[] message) {
		jmsTemplate.convertAndSend(queue, message);
	}
}
