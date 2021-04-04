package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail() {
        //Given
        Mail mail = Mail.builder()
                .mailFrom("xpc@o@.pl")
                .replyTo("")
                .mailTo("test@test.pl")
                .toCc("testToCc@test.pl")
                .subject("Test subject")
                .message("test message")
                .build();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mail.getMailFrom());
        mailMessage.setReplyTo(mail.getReplyTo());
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setCc(mail.getToCc());
        mailMessage.setSentDate(mail.getSentDate());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        //When
        emailService.send(mail);
        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }
}