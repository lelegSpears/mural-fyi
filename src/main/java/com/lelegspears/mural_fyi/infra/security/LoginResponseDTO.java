package com.lelegspears.mural_fyi.infra.security;

public record LoginResponseDTO(
        String accessToken,
        String tokenType,
        long expiresInMinutes
) {
    public LoginResponseDTO(String accessToken, long expiresInMinutes) {
        this(accessToken, "Bearer", expiresInMinutes);
    }
}