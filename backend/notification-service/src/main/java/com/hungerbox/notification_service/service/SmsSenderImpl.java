package com.hungerbox.notification_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hungerbox.notification_service.constant.NotificationConstants;
import com.hungerbox.notification_service.constant.NotificationConstants.NOTIF_TYPE;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;


@Service
public class SmsSenderImpl implements MessageSender {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromNumber;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void send(String to, String content) {
        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(fromNumber), 
                content)
            .create();
    }

    @Override
    public NOTIF_TYPE getType() {
        return NotificationConstants.NOTIF_TYPE.SMS;
    }
}