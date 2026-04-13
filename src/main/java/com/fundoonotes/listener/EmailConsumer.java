package com.fundoonotes.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailConsumer {

    @RabbitListener(queues = "mail.queue")
    public void consumeEmailEvent(String email) {
        log.info("Received event to send verification email to: {}", email);
        // simulate email sending
        log.info("Email sent successfully to: {}", email);
    }
}
