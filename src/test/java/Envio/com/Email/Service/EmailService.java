package Envio.com.Email.Service;

import Envio.com.Email.DTO.EmailRequestDTO;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(EmailRequestDTO emailRequest) throws MessagingException;
}
