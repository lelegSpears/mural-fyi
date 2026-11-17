package com.lelegspears.mural_fyi.entity.dto;

import com.lelegspears.mural_fyi.entity.User;

public record UserResponseDTO(
        Long userId,
        String name,
        String user
) {
    public UserResponseDTO(User entity) {
        this(entity.getUserId(), entity.getName(), entity.getUser());
    }
}