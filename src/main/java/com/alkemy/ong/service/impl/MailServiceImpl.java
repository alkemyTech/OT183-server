package com.alkemy.ong.service.impl;

import java.io.IOException;
import java.util.Locale;

import com.alkemy.ong.config.SendGridConfig;
import com.alkemy.ong.exception.EmailException;
import com.alkemy.ong.odt.MailFormat;
import com.alkemy.ong.service.IMailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements IMailService {

    private final SendGrid sendGrid;
    private final MessageSource messageSource;

    @Value("${app.email.sender}")
    private String sender;

    @Value("${app.email.enabled}")
    private boolean enabled;

    public MailServiceImpl(SendGrid sendGrid, MessageSource messageSource) {
        this.sendGrid = sendGrid;
        this.messageSource = messageSource;
    }

    private void sendTextEmail(MailFormat mailFormatter) {
        if (enabled != true) return;
        Email from = new Email(sender);
        String subject = mailFormatter.getSubject();
        Email to = new Email(mailFormatter.getMailReceiver());

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setTemplateId("d-1cdabdc3c46b474f84870571e351dfc8");

        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("name", mailFormatter.getName());
        personalization.addTo(to);

        mail.addPersonalization(personalization);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            if (
                    response.getStatusCode() != 200 &&
                            response.getStatusCode() != 201 &&
                            response.getStatusCode() != 202
            ) {
                throw new EmailException(messageSource.getMessage("error.email", null, Locale.US));
            }
        } catch (IOException ex) {
            throw new EmailException(messageSource.getMessage("error.email", null, Locale.US));
        }
    }

    @Override
    public void sendEmailByRegistration(String emailReceiver, String firstName) {

        MailFormat mail = new MailFormat();
        mail.setMailReceiver(emailReceiver);
        mail.setSubject("Welcome to Somos Más!");
        mail.setName(firstName);

        sendTextEmail(mail);
    }

    @Override
    public void sendEmailByFormComplete(String emailReceiver) {

        //TODO in the future find form by Email user
        MailFormat mail = new MailFormat();
        //TODO in the future indicate the receiver
        mail.setMailReceiver(emailReceiver);
        //TODO in the future indicate the sender
        mail.setMailReceiver("add the sender email");

        sendTextEmail(mail);
    }



}
