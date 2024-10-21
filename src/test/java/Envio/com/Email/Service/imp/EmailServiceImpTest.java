package Envio.com.Email.Service.imp;

import Envio.com.Email.DTO.EmailRequestDTO;
import Envio.com.Email.Entity.EmailEntity;
import Envio.com.Email.Repository.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmailServiceImpTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private EmailServiceImp emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmail() throws MessagingException {
        // Arrange
        EmailRequestDTO emailRequest = new EmailRequestDTO();
        emailRequest.setTo("test@example.com");
        emailRequest.setSubject("Test Subject");
        emailRequest.setTemplateModel(Map.of("name", "Test User"));

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("welcome-email"), any(Context.class))).thenReturn("Email Body");

        // Act
        emailService.sendEmail(emailRequest);

        // Assert
        verify(mailSender, times(1)).send(mimeMessage);
        verify(emailRepository, times(1)).save(any(EmailEntity.class));
    }

    @Test
    public void testSendEmailThrowsException() throws MessagingException {
        // Arrange
        EmailRequestDTO emailRequest = new EmailRequestDTO();
        emailRequest.setTo("test@example.com");
        emailRequest.setSubject("Test Subject");
        emailRequest.setTemplateModel(Map.of("name", "Test User"));

        when(mailSender.createMimeMessage()).thenThrow(new RuntimeException("Failed to send email"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> emailService.sendEmail(emailRequest));
    }

    @Test
    public void testEmailBodyGeneration() throws MessagingException {
        // Arrange
        EmailRequestDTO emailRequest = new EmailRequestDTO();
        emailRequest.setTo("test@example.com");
        emailRequest.setSubject("Test Subject");
        emailRequest.setTemplateModel(Map.of("name", "Test User"));

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("welcome-email"), any(Context.class))).thenReturn("Generated Email Body");

        // Act
        emailService.sendEmail(emailRequest);

        // Assert
        verify(templateEngine, times(1)).process(eq("welcome-email"), any(Context.class));
    }

    @Test
    public void testEmailNotSavedOnException() throws MessagingException {
        // Arrange
        EmailRequestDTO emailRequest = new EmailRequestDTO();
        emailRequest.setTo("test@example.com");
        emailRequest.setSubject("Test Subject");
        emailRequest.setTemplateModel(Map.of("name", "Test User"));

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(new RuntimeException("Failed to send email")).when(mailSender).send(mimeMessage);

        // Act
        try {
            emailService.sendEmail(emailRequest);
        } catch (RuntimeException e) {
            // Expected exception
        }

        // Assert
        verify(emailRepository, never()).save(any(EmailEntity.class));
    }
}