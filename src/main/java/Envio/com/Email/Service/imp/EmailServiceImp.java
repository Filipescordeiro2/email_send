package Envio.com.Email.Service.imp;

import Envio.com.Email.DTO.EmailRequestDTO;
import Envio.com.Email.Entity.EmailEntity;
import Envio.com.Email.Repository.EmailRepository;
import Envio.com.Email.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendEmail(EmailRequestDTO emailRequest) throws MessagingException {
        // Carregar o template e preencher com os dados
        Context context = new Context();
        context.setVariables(emailRequest.getTemplateModel());
        String body = templateEngine.process("welcome-email", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(emailRequest.getTo());
        helper.setSubject(emailRequest.getSubject());
        helper.setText(body, true);  // Usando o corpo do e-mail gerado pelo Thymeleaf

        // Enviar o e-mail
        mailSender.send(message);

        // Salvar no banco de dados
        EmailEntity email = new EmailEntity();
        email.setTo(emailRequest.getTo());
        email.setSubject(emailRequest.getSubject());
        email.setBody(body);
        email.setSentDate(LocalDateTime.now());
        email.setSentStatus(true);
        emailRepository.save(email);
    }
}
