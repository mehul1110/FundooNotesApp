package com.fundoonotes.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "notes.exchange";
    public static final String REMINDER_QUEUE = "reminder.queue";
    public static final String MAIL_QUEUE = "mail.queue";
    public static final String AUDIT_QUEUE = "audit.queue";

    @Bean
    public DirectExchange notesExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue reminderQueue() {
        return new Queue(REMINDER_QUEUE);
    }

    @Bean
    public Queue mailQueue() {
        return new Queue(MAIL_QUEUE);
    }

    @Bean
    public Queue auditQueue() {
        return new Queue(AUDIT_QUEUE);
    }

    @Bean
    public Binding reminderBinding(Queue reminderQueue, DirectExchange notesExchange) {
        return BindingBuilder.bind(reminderQueue).to(notesExchange).with("reminder.created");
    }

    @Bean
    public Binding mailBinding(Queue mailQueue, DirectExchange notesExchange) {
        return BindingBuilder.bind(mailQueue).to(notesExchange).with("user.registered");
    }

    @Bean
    public Binding auditBinding(Queue auditQueue, DirectExchange notesExchange) {
        return BindingBuilder.bind(auditQueue).to(notesExchange).with("note.audit");
    }
}
