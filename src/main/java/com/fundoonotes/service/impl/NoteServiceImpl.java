package com.fundoonotes.service.impl;

import com.fundoonotes.dto.request.NoteRequestDto;
import com.fundoonotes.dto.response.NoteResponseDto;
import com.fundoonotes.entity.Note;
import com.fundoonotes.entity.User;
import com.fundoonotes.exception.NoteNotFoundException;
import com.fundoonotes.exception.UserNotFoundException;
import com.fundoonotes.repository.NoteRepository;
import com.fundoonotes.repository.UserRepository;
import com.fundoonotes.service.NoteService;
import com.fundoonotes.messaging.EventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final EventPublisherService eventPublisherService;

    @Override
    public NoteResponseDto createNote(Long userId, NoteRequestDto request) {
        log.info("Creating note for user id: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setDescription(request.getDescription());
        note.setUser(user);

        Note savedNote = noteRepository.save(note);
        
        log.info("Note created successfully with id: {}", savedNote.getId());
        return mapToDto(savedNote);
    }

    @Override
    public List<NoteResponseDto> getAllNotes(Long userId) {
        log.info("Fetching all notes for user id: {}", userId);
        List<Note> notes = noteRepository.findByUserId(userId);
        return notes.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public NoteResponseDto pinNote(Long userId, Long noteId) {
        log.info("Pinning/Unpinning note id: {} for user id: {}", noteId, userId);
        Note note = fetchNoteByIdAndUserId(noteId, userId);
        note.setPinned(!note.isPinned());
        Note savedNote = noteRepository.save(note);
        try {
            eventPublisherService.publishAuditEvent("Note " + noteId + " pinned toggled by User " + userId);
        } catch (Exception e) {
            log.warn("Could not publish audit event: {}", e.getMessage());
        }
        return mapToDto(savedNote);
    }

    @Override
    public NoteResponseDto archiveNote(Long userId, Long noteId) {
        log.info("Archiving/Unarchiving note id: {} for user id: {}", noteId, userId);
        Note note = fetchNoteByIdAndUserId(noteId, userId);
        note.setArchived(!note.isArchived());
        Note savedNote = noteRepository.save(note);
        try {
            eventPublisherService.publishAuditEvent("Note " + noteId + " archive toggled by User " + userId);
        } catch (Exception e) {
            log.warn("Could not publish audit event: {}", e.getMessage());
        }
        return mapToDto(savedNote);
    }

    @Override
    public NoteResponseDto trashNote(Long userId, Long noteId) {
        log.info("Trashing/Untrashing note id: {} for user id: {}", noteId, userId);
        Note note = fetchNoteByIdAndUserId(noteId, userId);
        note.setTrashed(!note.isTrashed());
        Note savedNote = noteRepository.save(note);
        try {
            eventPublisherService.publishAuditEvent("Note " + noteId + " trash toggled by User " + userId);
        } catch (Exception e) {
            log.warn("Could not publish audit event: {}", e.getMessage());
        }
        return mapToDto(savedNote);
    }

    private Note fetchNoteByIdAndUserId(Long noteId, Long userId) {
        return noteRepository.findByIdAndUserId(noteId, userId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found or user unauthorized to access it"));
    }

    @Override
    @Cacheable(value = "notes", key = "#noteId")
    public NoteResponseDto getNoteById(Long userId, Long noteId) {
        log.info("Fetching note id: {} for user id: {}", noteId, userId);
        Note note = fetchNoteByIdAndUserId(noteId, userId);
        return mapToDto(note);
    }

    @Override
    @CachePut(value = "notes", key = "#noteId")
    public NoteResponseDto updateNote(Long userId, Long noteId, NoteRequestDto request) {
        log.info("Updating note id: {} for user id: {}", noteId, userId);
        Note note = fetchNoteByIdAndUserId(noteId, userId);
        note.setTitle(request.getTitle());
        note.setDescription(request.getDescription());
        Note savedNote = noteRepository.save(note);
        return mapToDto(savedNote);
    }

    @Override
    @CacheEvict(value = "notes", key = "#noteId")
    public void deleteNote(Long userId, Long noteId) {
        log.info("Deleting note id: {} for user id: {}", noteId, userId);
        Note note = fetchNoteByIdAndUserId(noteId, userId);
        noteRepository.delete(note);
    }

    private NoteResponseDto mapToDto(Note note) {
        NoteResponseDto dto = new NoteResponseDto();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setDescription(note.getDescription());
        dto.setPinned(note.isPinned());
        dto.setArchived(note.isArchived());
        dto.setTrashed(note.isTrashed());
        return dto;
    }
}
