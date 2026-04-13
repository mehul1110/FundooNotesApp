package com.fundoonotes.service.impl;

import com.fundoonotes.dto.request.LoginRequestDto;
import com.fundoonotes.dto.request.UserRegisterRequestDto;
import com.fundoonotes.dto.response.LoginResponseDto;
import com.fundoonotes.dto.response.UserResponseDto;
import com.fundoonotes.entity.User;
import com.fundoonotes.exception.InvalidCredentialsException;
import com.fundoonotes.exception.UserAlreadyExistsException;
import com.fundoonotes.exception.UserNotFoundException;
import com.fundoonotes.repository.UserRepository;
import com.fundoonotes.service.UserService;
import com.fundoonotes.messaging.EventPublisherService;
import com.fundoonotes.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;
    private final EventPublisherService eventPublisherService;

    @Override
    public UserResponseDto register(UserRegisterRequestDto request) {
        log.info("Registration started for email: {}", request.getEmail());
        
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        try {
            eventPublisherService.publishUserRegistration(savedUser.getEmail());
        } catch (Exception e) {
            log.error("Failed to publish registration event to RabbitMQ: {}", e.getMessage());
        }

        UserResponseDto response = new UserResponseDto();
        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setEmail(savedUser.getEmail());
        response.setMessage("User registered successfully");
        
        log.info("Registration successful for email: {}", request.getEmail());
        return response;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        log.info("Login attempt for email: {}", request.getEmail());
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        String token = tokenUtil.generateToken(user.getId());

        LoginResponseDto response = new LoginResponseDto();
        response.setToken(token);
        response.setMessage("Login successful");
        
        log.info("Login successful for email: {}", request.getEmail());
        return response;
    }
}
