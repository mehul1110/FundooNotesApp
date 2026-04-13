package com.fundoonotes.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuditConsumer {

    @RabbitListener(queues = "audit.queue")
    public void consumeAuditEvent(String auditMessage) {
        log.info("Received audit event: {}", auditMessage);
        // store audit log logic
        log.info("Audit log stored successfully.");
    }
}
