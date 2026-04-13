package com.fundoonotes.controller;

import com.fundoonotes.dto.request.ReminderRequestDto;
import com.fundoonotes.dto.response.ReminderDto;
import com.fundoonotes.exception.InvalidCredentialsException;
import com.fundoonotes.service.ReminderService;
import com.fundoonotes.util.TokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes/{noteId}/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;
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
    public ResponseEntity<ReminderDto> createReminder(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long noteId,
            @Valid @RequestBody ReminderRequestDto request) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.status(HttpStatus.CREATED).body(reminderService.createReminder(userId, noteId, request));
    }
}
