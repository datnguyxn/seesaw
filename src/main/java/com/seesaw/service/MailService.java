package com.seesaw.service;


import com.seesaw.model.Mail;

public interface MailService {

    void sendEmail(Mail mail, String path);

}
