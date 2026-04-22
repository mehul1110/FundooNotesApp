package com.fundoonotes.controller;

import com.fundoonotes.dto.request.NoteRequestDto;
import com.fundoonotes.dto.response.NoteResponseDto;
import com.fundoonotes.exception.InvalidCredentialsException;
import com.fundoonotes.service.NoteService;
import com.fundoonotes.util.TokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Slf4j
public class NoteController {

    private final NoteService noteService;
    private final TokenUtil tokenUtil;

    private Long getUserIdFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidCredentialsException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        if (!tokenUtil.validateToken(token)) {
            throw new InvalidCredentialsException("Invalid or expired token");
        }
        return tokenUtil.extractUserId(token);
    }

    @PostMapping("/")
    public ResponseEntity<NoteResponseDto> createNote(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody NoteRequestDto request) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(userId, request));
    }

    @GetMapping("/")
    public ResponseEntity<List<NoteResponseDto>> getAllNotes(
            @RequestHeader("Authorization") String authHeader) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(noteService.getAllNotes(userId));
    }



    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getNoteById(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(noteService.getNoteById(userId, id));
    }


}
