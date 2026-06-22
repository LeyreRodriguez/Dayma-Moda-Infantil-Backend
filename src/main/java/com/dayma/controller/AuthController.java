package com.dayma.controller;

import com.dayma.dto.AuthResponse;
import com.dayma.dto.LoginRequest;
import com.dayma.dto.RegisterRequest;
import com.dayma.dto.response.GenericResponseDto;
import com.dayma.enums.RoleEnum;
import com.dayma.model.User;
import com.dayma.repository.UserRepository;
import com.dayma.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<GenericResponseDto<AuthResponse>> signup(@RequestBody @Validated RegisterRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            return ResponseEntity.badRequest().build();
        }
        User user = User.builder()
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .role(RoleEnum.USER).build();


        userRepository.save(user);
        return ResponseEntity.ok(new GenericResponseDto<>(new AuthResponse(jwtService.generateToken(user))));
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponseDto<AuthResponse>> login(@RequestBody @Validated LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password())
        );
        User user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado tras autenticación"));
        return ResponseEntity.ok(new GenericResponseDto<>(new AuthResponse(jwtService.generateToken(user))));
    }

}
