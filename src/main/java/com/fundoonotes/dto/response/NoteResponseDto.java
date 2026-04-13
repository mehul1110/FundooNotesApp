package com.fundoonotes.dto.response;

import lombok.Data;

@Data
public class NoteResponseDto {
    private Long id;
    private String title;
    private String description;
    private boolean pinned;
    private boolean archived;
    private boolean trashed;
}
