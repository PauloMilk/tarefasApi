package com.fila.activemq.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.fila.activemq.service.EmailService;

@Component
public class Consumer {

	@Autowired
	EmailService emailService;
	
	@JmsListener(destination = "standalone.email")
	public void consume(String[] message) throws MessagingException {
		
		if(message[1].equals("enviarEmailConfirmacao")) {
			String codigo = Base64Utils.encodeToString(message[0].getBytes());
			emailService.enviarPedidoDeConfirmacaoDeCadastro(message[0], codigo);
			System.out.println("Enviando email para: " + message[0] + " para confirmacao.");
		}else if(message[1].equals("enviarEmailRecuperacao")) {
			emailService.enviarPedidoDeRedefinicaoDeSenha(message[0], message[2]);
			System.out.println("Enviando email para: " + message[0] + " para recuperacao.");
		}
		
	}
}
