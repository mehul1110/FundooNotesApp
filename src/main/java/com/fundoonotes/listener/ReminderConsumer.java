package com.fundoonotes.listener;

import com.fundoonotes.dto.response.ReminderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReminderConsumer {

    @RabbitListener(queues = "reminder.queue")
    public void consumeReminderEvent(ReminderDto reminder) {
        log.info("Received reminder event for note id: {} at time: {}", reminder.getNoteId(), reminder.getReminderTime());
        // simulate pushing notification to user
        log.info("Reminder notification sent to user id: {}", reminder.getUserId());
    }
}
