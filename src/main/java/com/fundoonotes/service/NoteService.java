package com.fundoonotes.service;

import com.fundoonotes.dto.request.NoteRequestDto;
import com.fundoonotes.dto.response.NoteResponseDto;

import java.util.List;

public interface NoteService {
    NoteResponseDto createNote(Long userId, NoteRequestDto request);
    List<NoteResponseDto> getAllNotes(Long userId);
    NoteResponseDto getNoteById(Long userId, Long noteId);
}
