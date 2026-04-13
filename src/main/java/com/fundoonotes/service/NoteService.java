package com.fundoonotes.service;

import com.fundoonotes.dto.request.NoteRequestDto;
import com.fundoonotes.dto.response.NoteResponseDto;

import java.util.List;

public interface NoteService {
    NoteResponseDto createNote(Long userId, NoteRequestDto request);
    List<NoteResponseDto> getAllNotes(Long userId);
    NoteResponseDto pinNote(Long userId, Long noteId);
    NoteResponseDto archiveNote(Long userId, Long noteId);
    NoteResponseDto trashNote(Long userId, Long noteId);
    NoteResponseDto getNoteById(Long userId, Long noteId);
    NoteResponseDto updateNote(Long userId, Long noteId, NoteRequestDto request);
    void deleteNote(Long userId, Long noteId);
}
