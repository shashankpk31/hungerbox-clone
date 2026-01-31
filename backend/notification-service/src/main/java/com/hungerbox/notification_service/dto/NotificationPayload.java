package com.hungerbox.notification_service.dto;

import java.io.Serializable;

public record NotificationPayload(
	    String to, 
	    String message, 
	    String type // "EMAIL" or "SMS"
	) implements Serializable {}
