package com.hungerbox.notification_service.service;

import com.hungerbox.notification_service.constant.NotificationConstants.NOTIF_TYPE;

public interface MessageSender {
	void send(String to, String content);

	NOTIF_TYPE getType();
}
