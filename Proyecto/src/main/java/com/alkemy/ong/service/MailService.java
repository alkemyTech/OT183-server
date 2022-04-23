package com.alkemy.ong.service;

import java.io.IOException;

import com.alkemy.ong.dto.MailFormatDTO;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    SendGrid sendGrid;

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);
	
	private String sendTextEmail(MailFormatDTO mailFormatter) throws IOException {   
            Email from = new Email(mailFormatter.getMailSender());
		    String subject = "The subject";

		    Email to = new Email(mailFormatter.getMailReciver());
		    Content content = new Content("text/plain", mailFormatter.getContent());
		    Mail mail = new Mail(from, subject, to, content);

		    Request request = new Request();
		    try {
		      request.setMethod(Method.POST);
		      request.setEndpoint("mail/send");
		      request.setBody(mail.build());
		      Response response = sendGrid.api(request);
		      logger.info(response.getBody());
		      return response.getBody();	     
		    } catch (IOException ex) {
		      throw ex;
		    }	   
	}

    public String sendEmailByRegistration(String emailReceiver) throws IOException{

        //TODO in the future find User Receiver by Email
        MailFormatDTO mail = new MailFormatDTO();
        //TODO in the future indicate the receiver
        mail.setMailReciver(emailReceiver);
        mail.setContent("Hi, u welcome");
        //TODO in the future indicate the sender
        mail.setMailReciver("add the sender email");

        return sendTextEmail(mail);
    }

    public String sendEmailByFormComplete(String emailReceiver) throws IOException{

        //TODO in the future find form by Email user
        MailFormatDTO mail = new MailFormatDTO();
        //TODO in the future indicate the receiver
        mail.setMailReciver(emailReceiver);
        mail.setContent("The form was succesfully send");
        //TODO in the future indicate the sender
        mail.setMailReciver("add the sender email");

        return sendTextEmail(mail);
    }

}
