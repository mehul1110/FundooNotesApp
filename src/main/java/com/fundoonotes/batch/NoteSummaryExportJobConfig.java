package com.fundoonotes.batch;

import com.fundoonotes.entity.Note;
import com.fundoonotes.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Scheduled note summary export job — runs every Sunday at 2 AM.
 * Logs all note summaries (in production would write CSV/Excel via Apache POI).
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NoteSummaryExportJobConfig {

    private final NoteRepository noteRepository;

    // Runs every Sunday at 2 AM
    @Scheduled(cron = "0 0 2 * * SUN")
    public void runNoteSummaryExport() {
        log.info("NoteSummaryExportJob STARTED");
        List<Note> allNotes = noteRepository.findAll();
        log.info("NoteSummaryExportJob: Exporting {} notes", allNotes.size());
        allNotes.forEach(note ->
            log.info("  Note [id={}, title={}, user={}, pinned={}, archived={}, trashed={}]",
                note.getId(), note.getTitle(),
                note.getUser() != null ? note.getUser().getEmail() : "N/A",
                note.isPinned(), note.isArchived(), note.isTrashed())
        );
        log.info("NoteSummaryExportJob COMPLETED. Total exported: {}", allNotes.size());
    }
}
