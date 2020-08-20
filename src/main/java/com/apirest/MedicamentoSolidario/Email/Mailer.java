package com.apirest.MedicamentoSolidario.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Mailer {
	@Autowired
	private JavaMailSender javaMailSender;

	public void enviar(Mensagem mensagem) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setFrom(mensagem.getRemetente());
		simpleMailMessage.setTo(mensagem.getDestinatario());
		simpleMailMessage.setSubject(mensagem.getAssunto());
		simpleMailMessage.setText(mensagem.getCorpo());
		javaMailSender.send(simpleMailMessage);
		System.out.println("Email enviado..");

	}

}
