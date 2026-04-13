package com.fundoonotes.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReminderDto {
    private Long id;
    private Long noteId;
    private Long userId;
    private LocalDateTime reminderTime;
    private String message;
}
