package com.hungerbox.notification_service.eventlistener;

import com.hungerbox.notification_service.config.RabbitConfig;
import com.hungerbox.notification_service.dto.NotificationPayload;
import com.hungerbox.notification_service.service.MessageSender;
import com.rabbitmq.client.Channel; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders; 
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);
    private final List<MessageSender> senders;

    public NotificationConsumer(List<MessageSender> senders) {
        this.senders = senders;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE, ackMode = "MANUAL")
    public void consume(NotificationPayload payload, 
                        Channel channel, 
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        
        log.info("Received notification request for: {} via {}", payload.to(), payload.type());

        try {
            // 1. Find the correct strategy (Email or SMS)
            MessageSender sender = senders.stream()
                    .filter(s -> s.getType().toString().equalsIgnoreCase(payload.type()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No provider found for type: " + payload.type()));

            // 2. Attempt to send
            sender.send(payload.to(), payload.message());

            // 3. Success: Manually Acknowledge (removes from main queue)
            channel.basicAck(tag, false);
            log.info("Notification sent successfully to {}", payload.to());

        } catch (Exception e) {
            log.error("Failed to process notification for {}: {}", payload.to(), e.getMessage());

            channel.basicNack(tag, false, false);
        }
    }
}