package com.lelegspears.mural_fyi.controller;

import com.lelegspears.mural_fyi.entity.User;
import com.lelegspears.mural_fyi.infra.security.LoginRequestDTO;
import com.lelegspears.mural_fyi.infra.security.LoginResponseDTO;
import com.lelegspears.mural_fyi.infra.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        Authentication authentication = authenticationManager.authenticate(authToken);

        User authenticatedUser = (User) authentication.getPrincipal();
        String jwt = tokenService.generateToken(authenticatedUser);

        return ResponseEntity.ok(new LoginResponseDTO(jwt, tokenService.getExpirationMinutes()));
    }
}