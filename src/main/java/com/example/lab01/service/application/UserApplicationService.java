package com.example.lab01.service.application;

import com.example.lab01.dto.CreateUserDto;
import com.example.lab01.dto.UserDto;
import com.example.lab01.model.domain.User;
import com.example.lab01.service.domain.UserDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserApplicationService {

    private final UserDomainService userDomainService;

    public UserApplicationService(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    public List<UserDto> findAll() {
        return userDomainService.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserDto findById(Long id) {
        User user = userDomainService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return toDto(user);
    }

    public UserDto findByUsername(String username) {
        User user = userDomainService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        return toDto(user);
    }

    public UserDto create(CreateUserDto createDto) {
        User user = userDomainService.save(
                createDto.username(),
                createDto.password(),
                createDto.name(),
                createDto.surname(),
                createDto.role()
        );
        return toDto(user);
    }

    public void deleteById(Long id) {
        userDomainService.deleteById(id);
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getRole()
        );
    }
}
