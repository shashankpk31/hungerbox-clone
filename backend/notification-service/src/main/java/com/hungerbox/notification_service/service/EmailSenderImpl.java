package com.hungerbox.notification_service.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.hungerbox.notification_service.constant.NotificationConstants.NOTIF_TYPE;

@Service
public class EmailSenderImpl implements MessageSender {

	private final JavaMailSender mailSender;

	public EmailSenderImpl(JavaMailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}

	@Override
	public void send(String to, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("HungerBox Verification Code");
		message.setText(content);
		mailSender.send(message);
	}

	@Override
	public NOTIF_TYPE getType() {
		return NOTIF_TYPE.EMAIL;
	}

}
