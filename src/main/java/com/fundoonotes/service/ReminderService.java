package com.fundoonotes.service;

import com.fundoonotes.dto.request.ReminderRequestDto;
import com.fundoonotes.dto.response.ReminderDto;
import com.fundoonotes.entity.Note;
import com.fundoonotes.entity.Reminder;
import com.fundoonotes.exception.NoteNotFoundException;
import com.fundoonotes.messaging.EventPublisherService;
import com.fundoonotes.repository.NoteRepository;
import com.fundoonotes.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final NoteRepository noteRepository;
    private final EventPublisherService eventPublisherService;

    public ReminderDto createReminder(Long userId, Long noteId, ReminderRequestDto request) {
        log.info("Creating reminder for note id: {}", noteId);
        Note note = noteRepository.findByIdAndUserId(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or unauthorized"));

        Reminder reminder = new Reminder();
        reminder.setReminderTime(request.getReminderTime());
        reminder.setMessage(request.getMessage());
        reminder.setNote(note);

        Reminder saved = reminderRepository.save(reminder);

        ReminderDto dto = new ReminderDto();
        dto.setId(saved.getId());
        dto.setNoteId(note.getId());
        dto.setUserId(userId);
        dto.setReminderTime(saved.getReminderTime());
        dto.setMessage(saved.getMessage());

        try {
            eventPublisherService.publishReminderEvent(dto);
        } catch (Exception e) {
            log.warn("Failed to publish reminder event: {}", e.getMessage());
        }
        log.info("Reminder created successfully with id: {}", saved.getId());

        return dto;
    }
}
