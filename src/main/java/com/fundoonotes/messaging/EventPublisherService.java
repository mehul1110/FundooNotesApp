package com.fundoonotes.messaging;

import com.fundoonotes.dto.response.ReminderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisherService {

    private final RabbitTemplate rabbitTemplate;

    public void publishUserRegistration(String email) {
        log.info("Publishing user registration event for: {}", email);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, "user.registered", email);
    }

    public void publishReminderEvent(ReminderDto reminderDto) {
        log.info("Publishing reminder event for note: {}", reminderDto.getNoteId());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, "reminder.created", reminderDto);
    }

    public void publishAuditEvent(String noteAction) {
        log.info("Publishing audit event: {}", noteAction);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, "note.audit", noteAction);
    }
}
