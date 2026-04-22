package com.fundoonotes.controller;

import com.fundoonotes.dto.request.UserRegisterRequestDto;
import com.fundoonotes.dto.response.UserResponseDto;
import com.fundoonotes.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing user registration.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegisterRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }


}
