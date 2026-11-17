package com.lelegspears.mural_fyi.controller;

import com.lelegspears.mural_fyi.entity.User;
import com.lelegspears.mural_fyi.entity.dto.UserCreateDTO;
import com.lelegspears.mural_fyi.entity.dto.UserResponseDTO;
import com.lelegspears.mural_fyi.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Administradores", description = "Gerenciamento de contas Administrativas")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(
            @RequestBody @Valid UserCreateDTO dto,
            UriComponentsBuilder uriBuilder) {

        UserResponseDTO createdUser = userService.create(dto);

        URI uri = uriBuilder
                .path("/users/{id}")
                .buildAndExpand(createdUser.userId())
                .toUri();

        return ResponseEntity.created(uri).body(createdUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id,
            @AuthenticationPrincipal User loggedUser) {

        userService.deleteById(id, loggedUser);
        return ResponseEntity.noContent().build();
    }
}