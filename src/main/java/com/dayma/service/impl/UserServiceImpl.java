package com.dayma.service.impl;

import com.dayma.dto.AuthResponse;
import com.dayma.dto.GoogleLoginRequest;
import com.dayma.dto.LoginRequest;
import com.dayma.dto.RegisterRequest;
import com.dayma.enums.RoleEnum;
import com.dayma.exception.UserAlreadyRegistered;
import com.dayma.model.User;
import com.dayma.repository.UserRepository;
import com.dayma.security.JwtService;
import com.dayma.service.EmailService;
import com.dayma.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final EmailService emailService;

    @Value("${google.client-id}")
    private String googleClientId;

    @Value("${app.front-base-url}")
    private String baseUrl;

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No authenticated user");
        }
        return userRepository.findByEmail(auth.getName()).orElse(null);
    }

    @Override
    public AuthResponse signup(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new UserAlreadyRegistered("Este usuario ya se encuentra registrado");
        }
        String verificationCode = UUID.randomUUID().toString();
        User user = User.builder()
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .name(registerRequest.name())
                .registrationDate(LocalDateTime.now())
                .verified(false)
                .verificationCode(verificationCode)
                .role(RoleEnum.USER).build();

        userRepository.save(user);
        emailService.sendVerificationEmail(user.getEmail(), verificationCode);
        return new AuthResponse(jwtService.generateToken(user));
    }


    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
        } catch (DisabledException e) {
            throw new RuntimeException("Debes verificar tu cuenta antes de iniciar sesión. Revisa tu correo.");
        }
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado tras autenticación"));
        return new AuthResponse(jwtService.generateToken(user));
    }

    @Override
    public String verify(String code) {
        User user = userRepository.findByVerificationCode(code)
                .orElseThrow(() -> new RuntimeException("Código de verificación inválido"));
        user.setVerified(true);
        userRepository.save(user);

        return user.getVerified()
                ? buildSuccessPage()
                : buildErrorPage();

    }



    private String buildSuccessPage() {
        return """
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <title>Cuenta verificada - Dayma Moda Infantil</title>
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,400;0,600;1,400&family=Montserrat:wght@400;500&display=swap" rel="stylesheet">
        </head>
        <body style="margin:0; padding:0; font-family:'Montserrat','Helvetica Neue',Arial,sans-serif; background:#fbf9f4;">
            <div style="max-width:500px; margin:60px auto; background:#ffffff; border-radius:16px;
                        box-shadow:0 10px 30px -5px rgba(23,44,33,0.08); padding:40px; text-align:center;">
                <div style="font-size:52px; margin-bottom:8px;">\u2728</div>
                <h2 style="color:#1b1c19; font-family:'Playfair Display',Georgia,'Times New Roman',serif; font-size:22px; font-weight:600; margin:0 0 16px;">
                    \u00a1Cuenta verificada!
                </h2>
                <p style="color:#424844; font-size:15px; line-height:1.7; margin:0 0 24px;">
                    Tu cuenta en <strong>Dayma Moda Infantil</strong> ha sido activada correctamente.
                    Ya puedes iniciar sesi\u00f3n y empezar a descubrir nuestras colecciones.
                </p>
                <a href="%s"
                   style="display:inline-block; background:#2d4236; color:#ffffff; text-decoration:none;
                          padding:14px 36px; border-radius:30px; font-size:15px; font-weight:600;
                          font-family:'Montserrat','Helvetica Neue',Arial,sans-serif; letter-spacing:0.5px;">
                    Ir a iniciar sesi\u00f3n
                </a>
            </div>
        </body>
        </html>
        """.formatted(baseUrl);
    }

    @Override
    public AuthResponse googleLogin(GoogleLoginRequest request) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(request.idToken());
            if (idToken == null) {
                throw new RuntimeException("Token de Google inválido");
            }

            String email = idToken.getPayload().getEmail();
            String name = (String) idToken.getPayload().get("name");

            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                user = User.builder()
                        .email(email)
                        .name(name)
                        .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                        .verified(true)
                        .registrationDate(LocalDateTime.now())
                        .role(RoleEnum.USER)
                        .build();
            } else {
                user.setVerified(true);
                user.setVerificationCode(null);
            }

            userRepository.save(user);
            return new AuthResponse(jwtService.generateToken(user));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar el token de Google", e);
        }
    }

    @Override
    public AuthResponse googleSignup(GoogleLoginRequest request) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(request.idToken());
            if (idToken == null) {
                throw new RuntimeException("Token de Google inválido");
            }

            String email = idToken.getPayload().getEmail();
            String name = (String) idToken.getPayload().get("name");

            if (userRepository.existsByEmail(email)) {
                throw new UserAlreadyRegistered("Este usuario ya se encuentra registrado");
            }

            User user = User.builder()
                    .email(email)
                    .name(name)
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .verified(true)
                    .registrationDate(LocalDateTime.now())
                    .role(RoleEnum.USER)
                    .build();

            userRepository.save(user);
            return new AuthResponse(jwtService.generateToken(user));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar el token de Google", e);
        }
    }

    @Override
    public void newsletterSubscribe() {
        User user = getCurrentUser();
        if (user == null) {
            throw new RuntimeException("Usuario no autenticado");
        }
        user.setNewsletter(true);
        userRepository.save(user);
    }



    @Override
    public String newsletterUnsubscribe(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setNewsletter(false);
        userRepository.save(user);
        return """
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <title>Desuscrito - Dayma Moda Infantil</title>
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,400;0,600;1,400&family=Montserrat:wght@400;500&display=swap" rel="stylesheet">
        </head>
        <body style="margin:0; padding:0; font-family:'Montserrat','Helvetica Neue',Arial,sans-serif; background:#fbf9f4;">
            <div style="max-width:500px; margin:60px auto; background:#ffffff; border-radius:16px;
                        box-shadow:0 10px 30px -5px rgba(23,44,33,0.08); padding:40px; text-align:center;">
                <h2 style="color:#1b1c19; font-family:'Playfair Display',Georgia,'Times New Roman',serif; font-size:22px; font-weight:600; margin:0 0 16px;">
                    Te has desuscrito correctamente
                </h2>
                <p style="color:#424844; font-size:15px; line-height:1.7; margin:0 0 24px;">
                    Ya no recibir\u00e1s m\u00e1s correos de nuestra newsletter.
                    Si cambias de opini\u00f3n, puedes volver a suscribirte desde tu perfil.
                </p>
                <a href="%s"
                   style="color:#81515a; font-size:13px; text-decoration:none; border-bottom:1px solid #81515a;">
                    Volver a Dayma
                </a>
            </div>
        </body>
        </html>
        """.formatted(baseUrl);

    }

    @Override
    public void newsletterUnsubscribe() {
        User user = getCurrentUser();
        if (user == null) {
            throw new RuntimeException("Usuario no autenticado");
        }
        user.setNewsletter(false);
        userRepository.save(user);
    }

    @Override
    public long countByRegistrationDateBetween(LocalDateTime start, LocalDateTime end) {
        return userRepository.countByRegistrationDateBetween(start, end);
    }

    private String buildErrorPage() {
        return """
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <title>Error de verificaci\u00f3n - Dayma Moda Infantil</title>
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,400;0,600;1,400&family=Montserrat:wght@400;500&display=swap" rel="stylesheet">
        </head>
        <body style="margin:0; padding:0; font-family:'Montserrat','Helvetica Neue',Arial,sans-serif; background:#fbf9f4;">
            <div style="max-width:500px; margin:60px auto; background:#ffffff; border-radius:16px;
                        box-shadow:0 10px 30px -5px rgba(23,44,33,0.08); padding:40px; text-align:center;">
                <div style="font-size:52px; margin-bottom:8px;">\u26a0\ufe0f</div>
                <h2 style="color:#1b1c19; font-family:'Playfair Display',Georgia,'Times New Roman',serif; font-size:22px; font-weight:600; margin:0 0 16px;">
                    Enlace inv\u00e1lido o expirado
                </h2>
                <p style="color:#424844; font-size:15px; line-height:1.7; margin:0;">
                    Este enlace de verificaci\u00f3n ya no es v\u00e1lido. Solicita uno nuevo desde la app.
                </p>
            </div>
        </body>
        </html>
        """;
    }
}
