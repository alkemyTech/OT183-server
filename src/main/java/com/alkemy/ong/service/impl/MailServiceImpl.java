package com.alkemy.ong.service.impl;

import java.io.IOException;
import java.util.Locale;

import com.alkemy.ong.exception.EmailException;
import com.alkemy.ong.exception.EmailSenderException;
import com.alkemy.ong.odt.MailFormat;
import com.alkemy.ong.service.IMailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;

import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements IMailService {

    private final SendGrid sendGrid;
    private final MessageSource messageSource;

    @Value("${app.email.sender}")
    private String sender;

    @Value("${app.email.enabled}")
    private boolean enabled;

    @Value("${app.email.template_id}")
    private String templateId;

    public MailServiceImpl(SendGrid sendGrid, MessageSource messageSource) {
        this.sendGrid = sendGrid;
        this.messageSource = messageSource;
    }

    private void sendTextEmail(MailFormat mailFormatter) {
        if (enabled != true) return;
        if (sender == null) throw new EmailSenderException(
                messageSource.getMessage("error.email_sender", null, Locale.US)
        );
        Email from = new Email(sender);
        String subject = mailFormatter.getSubject();
        Email to = new Email(mailFormatter.getMailReceiver());

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setTemplateId(templateId);

        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("name", mailFormatter.getName());
        personalization.addDynamicTemplateData("subject", mailFormatter.getSubject());
        personalization.addDynamicTemplateData("content", mailFormatter.getContent());
        personalization.addTo(to);

        mail.addPersonalization(personalization);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            if (
                    response.getStatusCode() != HttpStatus.OK.value() &&
                            response.getStatusCode() != HttpStatus.CREATED.value() &&
                            response.getStatusCode() != HttpStatus.ACCEPTED.value()
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
        mail.setSubject("Welcome " + firstName + "! Enjoy your stay!");
        mail.setContent(messageSource.getMessage("email.registration.content", null, Locale.US));
        mail.setName(firstName);

        sendTextEmail(mail);
    }

    @Override
    public void sendEmailByFormComplete(String emailReceiver) {

        MailFormat mail = new MailFormat();
        mail.setMailReceiver(emailReceiver);
        mail.setSubject(messageSource.getMessage("email.contact.subject", null, Locale.US));
        mail.setContent(messageSource.getMessage("email.contact.content", null, Locale.US));

        sendTextEmail(mail);
    }



}
