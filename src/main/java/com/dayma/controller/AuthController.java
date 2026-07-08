package com.dayma.controller;

import com.dayma.dto.AuthResponse;
import com.dayma.dto.GoogleLoginRequest;
import com.dayma.dto.LoginRequest;
import com.dayma.dto.RegisterRequest;
import com.dayma.dto.UserDto;
import com.dayma.dto.response.GenericResponseDto;
import com.dayma.mapper.UserMapper;
import com.dayma.model.User;
import com.dayma.repository.UserRepository;
import com.dayma.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/signup")
    public ResponseEntity<GenericResponseDto<AuthResponse>> signup(@RequestBody @Validated RegisterRequest req) {
        return ResponseEntity.ok(new GenericResponseDto<>(userService.signup(req)));
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponseDto<AuthResponse>> login(@RequestBody @Validated LoginRequest req) {
        return ResponseEntity.ok(new GenericResponseDto<>(userService.login(req)));
    }

    @GetMapping("/me")
    public ResponseEntity<GenericResponseDto<UserDto>> login() {
        return ResponseEntity.ok(new GenericResponseDto<>(userMapper.toDto(userService.getCurrentUser())));
    }

    @PostMapping("/google/login")
    public ResponseEntity<GenericResponseDto<AuthResponse>> googleLogin(@RequestBody @Validated GoogleLoginRequest req) {
        return ResponseEntity.ok(new GenericResponseDto<>(userService.googleLogin(req)));
    }

    @PostMapping("/google/register")
    public ResponseEntity<GenericResponseDto<AuthResponse>> googleSignup(@RequestBody @Validated GoogleLoginRequest req) {
        return ResponseEntity.ok(new GenericResponseDto<>(userService.googleSignup(req)));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String code) {
        String html = userService.verify(code);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html);
    }

}
