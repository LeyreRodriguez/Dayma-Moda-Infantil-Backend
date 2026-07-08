package com.dayma.scheduler;

import com.dayma.model.User;
import com.dayma.repository.UserRepository;
import com.dayma.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsletterScheduler {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Scheduled(cron = "${newsletter.cron}")
    public void sendNewsletter() {
        List<User> subscribers = userRepository.findByNewsletterTrue();
        log.info("Enviando newsletter a {} suscriptores", subscribers.size());

        for (User user : subscribers) {
            try {
                emailService.sendNewsletter(user.getEmail());
                log.debug("Newsletter enviada a {}", user.getEmail());
            } catch (Exception e) {
                log.error("Error al enviar newsletter a {}: {}", user.getEmail(), e.getMessage());
            }
        }
    }
}
