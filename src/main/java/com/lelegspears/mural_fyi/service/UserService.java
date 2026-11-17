package com.lelegspears.mural_fyi.service;

import com.lelegspears.mural_fyi.entity.User;
import com.lelegspears.mural_fyi.entity.dto.UserCreateDTO;
import com.lelegspears.mural_fyi.entity.dto.UserResponseDTO;
import com.lelegspears.mural_fyi.exception.BusinessRuleException;
import com.lelegspears.mural_fyi.exception.ResourceNotFoundException;
import com.lelegspears.mural_fyi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        User user = findUserOrThrow(id);
        return new UserResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO create(UserCreateDTO dto) {
        if (userRepository.findByUser(dto.user()).isPresent()) {
            throw new BusinessRuleException("Este nome de usuário já está em uso.");
        }

        String encodedPassword = passwordEncoder.encode(dto.password());
        User newUser = new User(dto.name(), dto.user(), encodedPassword);

        User savedUser = userRepository.save(newUser);
        return new UserResponseDTO(savedUser);
    }

    @Transactional
    public void deleteById(Long id, User loggedUser) {
        User targetUser = findUserOrThrow(id);

        if (targetUser.getUserId().equals(loggedUser.getUserId())) {
            throw new BusinessRuleException("Você não pode excluir a sua própria conta de administrador.");
        }

        if (userRepository.count() <= 1) {
            throw new BusinessRuleException("Não é possível excluir o único administrador cadastrado no sistema.");
        }

        userRepository.delete(targetUser);
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador não encontrado com o ID: " + id));
    }
}