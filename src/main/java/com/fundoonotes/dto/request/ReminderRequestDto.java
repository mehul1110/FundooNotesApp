package com.fundoonotes.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReminderRequestDto {
    @NotNull(message = "Reminder time cannot be null")
    @Future(message = "Reminder time must be in the future")
    private LocalDateTime reminderTime;
    
    private String message;
}
