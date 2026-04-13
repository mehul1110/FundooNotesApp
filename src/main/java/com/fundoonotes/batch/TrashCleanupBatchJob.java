package com.fundoonotes.batch;

import com.fundoonotes.entity.Note;
import com.fundoonotes.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Scheduled trash cleanup job — runs daily at midnight.
 * Permanently deletes all trashed notes (simulates Spring Batch cleanup job).
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TrashCleanupBatchJob {

    private final NoteRepository noteRepository;

    // Runs every day at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void runTrashCleanup() {
        log.info("TrashCleanupJob STARTED");
        List<Note> trashedNotes = noteRepository.findByTrashedTrue();
        if (trashedNotes.isEmpty()) {
            log.info("TrashCleanupJob: No trashed notes found.");
        } else {
            log.info("TrashCleanupJob: Permanently deleting {} trashed notes", trashedNotes.size());
            noteRepository.deleteAll(trashedNotes);
            log.info("TrashCleanupJob COMPLETED successfully.");
        }
    }
}
