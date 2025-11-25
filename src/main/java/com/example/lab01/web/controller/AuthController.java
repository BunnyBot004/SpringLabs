package com.example.lab01.web.controller;

import com.example.lab01.dto.CreateUserDto;
import com.example.lab01.dto.LoginResponseDto;
import com.example.lab01.dto.LoginUserDto;
import com.example.lab01.dto.UserDto;
import com.example.lab01.security.JwtHelper;
import com.example.lab01.service.application.UserApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication management endpoints")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtHelper jwtHelper;
    private final UserApplicationService userApplicationService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService,
                          JwtHelper jwtHelper,
                          UserApplicationService userApplicationService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
        this.userApplicationService = userApplicationService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account with username, password, and role")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserDto createUserDto) {
        UserDto userDto = userApplicationService.create(createUserDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user and return JWT token")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.username(),
                        loginUserDto.password()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginUserDto.username());
        final String token = jwtHelper.generateToken(userDetails);
        final UserDto userDto = userApplicationService.findByUsername(loginUserDto.username());

        LoginResponseDto response = new LoginResponseDto(token, userDto);
        return ResponseEntity.ok(response);
    }
}
