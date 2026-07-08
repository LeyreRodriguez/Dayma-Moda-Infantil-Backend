package com.dayma.controller;

import com.dayma.dto.response.GenericResponseDto;
import com.dayma.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/newsletter")
@RestController
@RequiredArgsConstructor
public class NewsletterController {

    private final UserService userService;

    @PostMapping("/subscribe")
    public ResponseEntity<GenericResponseDto<String>> subscribe() {
        userService.newsletterSubscribe();
        return ResponseEntity.ok(new GenericResponseDto<>("Suscrito a la newsletter correctamente"));
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<GenericResponseDto<String>> unsubscribe() {
        userService.newsletterUnsubscribe();
        return ResponseEntity.ok(new GenericResponseDto<>("Desuscrito de la newsletter correctamente"));
    }

    @GetMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribeByEmail(@RequestParam String email) {
        String html = userService.newsletterUnsubscribe(email);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html);
    }
}
