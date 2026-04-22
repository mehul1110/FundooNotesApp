package com.fundoonotes.dto.response;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String email;
    private String message;
}
