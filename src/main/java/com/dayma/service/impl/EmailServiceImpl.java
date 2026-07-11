package com.dayma.service.impl;

import com.dayma.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.backend-base-url}")
    private String backendBaseUrl;

    @Value("${app.front-base-url}")
    private String frontBaseUrl;

    @Override
    public void sendVerificationEmail(String to, String code) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Verifica tu cuenta - Dayma Moda Infantil");

            String verificationUrl = backendBaseUrl + "/api/auth/verify?code=" + code;

            String htmlContent = """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,400;0,600;0,700;1,400&family=Montserrat:wght@400;500;600&display=swap" rel="stylesheet">
            </head>
            <body style="margin:0; padding:0; background-color:#fbf9f4; font-family: 'Montserrat', 'Helvetica Neue', Arial, sans-serif;">
                <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="background-color:#fbf9f4; padding: 30px 0;">
                    <tr>
                        <td align="center">
                            <table role="presentation" width="600" cellpadding="0" cellspacing="0" style="background-color:#ffffff; border-radius:16px; overflow:hidden; box-shadow: 0 10px 30px -5px rgba(23,44,33,0.08);">

                                <!-- Header -->
                                <tr>
                                    <td style="background-color:#2d4236; padding: 40px 20px; text-align:center;">
                                        <h1 style="margin:0; color:#ffffff; font-family:'Playfair Display', Georgia, 'Times New Roman', serif; font-size:28px; font-style:italic; letter-spacing:0.5px;">
                                            Dayma
                                        </h1>
                                        <p style="margin:12px 0 0; color:#d0e8d7; font-size:13px; letter-spacing:2px; text-transform:uppercase;">
                                            Moda Infantil
                                        </p>
                                    </td>
                                </tr>

                                <!-- Body -->
                                <tr>
                                    <td style="padding: 45px 40px;">
                                        <h2 style="color:#1b1c19; font-family:'Playfair Display', Georgia, 'Times New Roman', serif; font-size:22px; font-weight:600; margin-top:0;">
                                            Bienvenido a la familia Dayma
                                        </h2>
                                        <p style="color:#424844; font-size:15px; line-height:1.7;">
                                            Gracias por registrarte en <strong>Dayma Moda Infantil</strong>.
                                            Estamos muy contentos de tenerte con nosotros. Solo falta un paso
                                            para activar tu cuenta y empezar a descubrir nuestras colecciones.
                                        </p>

                                        <div style="text-align:center; margin: 40px 0;">
                                            <a href="%s"
                                                style="background-color:#2d4236; color:#ffffff; text-decoration:none;
                                                       padding: 15px 40px; border-radius: 30px; font-size:15px;
                                                       font-weight:600; font-family:'Montserrat', 'Helvetica Neue', Arial, sans-serif;
                                                       display:inline-block; letter-spacing:0.5px;">
                                                Verificar mi cuenta
                                            </a>
                                        </div>

                                        <p style="color:#737873; font-size:13px; line-height:1.6;">
                                            Si el botón no funciona, copia y pega este enlace en tu navegador:<br>
                                            <a href="%s" style="color:#81515a;">%s</a>
                                        </p>

                                        <hr style="border:none; border-top:1px solid #e4e2dd; margin:35px 0;">

                                        <p style="color:#a0a5a0; font-size:12px;">
                                            Si no has creado esta cuenta, puedes ignorar este mensaje con tranquilidad.
                                        </p>
                                    </td>
                                </tr>

                                <!-- Footer -->
                                <tr>
                                    <td style="background-color:#fbf9f4; padding: 25px; text-align:center;">
                                        <table role="presentation" width="100%%" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td style="text-align:center; padding-bottom:8px;">
                                                    <span style="color:#2d4236; font-family:'Playfair Display', Georgia, 'Times New Roman', serif; font-size:16px; font-style:italic;">Dayma</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="text-align:center;">
                                                    <p style="color:#a0a5a0; font-size:12px; margin:0;">
                                                        C/ Matías Zurita, n.º 11 · Telde, Gran Canaria<br>
                                                        &copy; 2026 Dayma Moda Infantil · Todos los derechos reservados
                                                    </p>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>

                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """.formatted(verificationUrl, verificationUrl, verificationUrl);

            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo de verificación", e);
        }
    }

    @Override
    public void sendNewsletter(String to) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Novedades - Dayma Moda Infantil");

            String htmlContent = """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,400;0,600;0,700;1,400&family=Montserrat:wght@400;500;600&display=swap" rel="stylesheet">
            </head>
            <body style="margin:0; padding:0; background-color:#fbf9f4; font-family: 'Montserrat', 'Helvetica Neue', Arial, sans-serif;">
                <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="background-color:#fbf9f4; padding: 30px 0;">
                    <tr>
                        <td align="center">
                            <table role="presentation" width="600" cellpadding="0" cellspacing="0" style="background-color:#ffffff; border-radius:16px; overflow:hidden; box-shadow: 0 10px 30px -5px rgba(23,44,33,0.08);">

                                <!-- Header -->
                                <tr>
                                    <td style="background-color:#2d4236; padding: 40px 20px; text-align:center;">
                                        <h1 style="margin:0; color:#ffffff; font-family:'Playfair Display', Georgia, 'Times New Roman', serif; font-size:28px; font-style:italic; letter-spacing:0.5px;">
                                            Dayma
                                        </h1>
                                        <p style="margin:12px 0 0; color:#d0e8d7; font-size:13px; letter-spacing:2px; text-transform:uppercase;">
                                            Moda Infantil
                                        </p>
                                    </td>
                                </tr>

                                <!-- Body -->
                                <tr>
                                    <td style="padding: 45px 40px;">
                                        <h2 style="color:#1b1c19; font-family:'Playfair Display', Georgia, 'Times New Roman', serif; font-size:22px; font-weight:600; margin-top:0;">
                                            Novedades para ti
                                        </h2>
                                        <p style="color:#424844; font-size:15px; line-height:1.7;">
                                            Queremos compartir contigo las &uacute;ltimas novedades en ropa infantil.
                                            Nuevas colecciones, colores y dise&ntilde;os pensados con todo el amor
                                            para los m&aacute;s peque&ntilde;os de la casa.
                                        </p>

                                        <div style="text-align:center; margin: 40px 0;">
                                            <a href="%s"
                                                style="background-color:#2d4236; color:#ffffff; text-decoration:none;
                                                       padding: 15px 40px; border-radius: 30px; font-size:15px;
                                                       font-weight:600; font-family:'Montserrat', 'Helvetica Neue', Arial, sans-serif;
                                                       display:inline-block; letter-spacing:0.5px;">
                                                Ver colecciones
                                            </a>
                                        </div>

                                        <hr style="border:none; border-top:1px solid #e4e2dd; margin:35px 0;">

                                        <p style="color:#a0a5a0; font-size:12px;">
                                            Si no deseas recibir m&aacute;s correos,
                                            <a href="%s/api/newsletter/unsubscribe?email=%s" style="color:#81515a;">cancela tu suscripci&oacute;n aqu&iacute;</a>.
                                        </p>
                                    </td>
                                </tr>

                                <!-- Footer -->
                                <tr>
                                    <td style="background-color:#fbf9f4; padding: 25px; text-align:center;">
                                        <table role="presentation" width="100%%" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td style="text-align:center; padding-bottom:8px;">
                                                    <span style="color:#2d4236; font-family:'Playfair Display', Georgia, 'Times New Roman', serif; font-size:16px; font-style:italic;">Dayma</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="text-align:center;">
                                                    <p style="color:#a0a5a0; font-size:12px; margin:0;">
                                                        C/ Matías Zurita, n.º 11 · Telde, Gran Canaria<br>
                                                        &copy; 2026 Dayma Moda Infantil · Todos los derechos reservados
                                                    </p>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>

                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """.formatted(frontBaseUrl, frontBaseUrl, to);

            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar la newsletter", e);
        }
    }
}
