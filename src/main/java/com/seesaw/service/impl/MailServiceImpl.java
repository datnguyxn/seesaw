package com.seesaw.service.impl;

import com.seesaw.model.Mail;
import com.seesaw.model.MailDetail;
import com.seesaw.service.MailService;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    private final ThymeleafServiceImp thymeleafServiceImpl;

    private final MailDetail mailDetail;

    @Override
    public void sendEmail(Mail mail) {

        Properties props = new Properties();
//        props.put("mail.transport.protocol", mailDetail.getProtocol());
        props.put("mail.smtp.auth", mailDetail.getAuth());
        props.put("mail.smtp.starttls.enable", mailDetail.getStarttls());
        props.put("mail.smtp.host", mailDetail.getHost());
        props.put("mail.smtp.port", mailDetail.getPort());
        try {
                Session session = Session.getDefaultInstance(props,
                        new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                if ((mailDetail.getUsername() != null) && (mailDetail.getUsername().length() > 0) && (mailDetail.getPassword()) != null
                                        && (mailDetail.getPassword().length() > 0)) {

                                    return new PasswordAuthentication(mailDetail.getUsername(), mailDetail.getPassword());
                                }
                                return null;
                            }
                        });
                Transport transport = session.getTransport();
                InternetAddress addressFrom = new InternetAddress(mailDetail.getUsername());
                MimeMessage message = new MimeMessage(session);
                message.setFrom('"' + "Seesaw" + '"' + "<" + mailDetail.getUsername() + ">");
//                message.setSender(addressFrom);
                message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mail.getTo()));
                String html = thymeleafServiceImpl.createContent("mail-sender.html", mail);
                message.setSubject(mail.getSubject());
                message.setContent(html, "text/html; charset=utf-8");
                Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
      }

    }

}
