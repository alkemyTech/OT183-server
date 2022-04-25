package com.alkemy.ong.config;

import com.sendgrid.SendGrid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {
    //TODO in the future indicate the API KEY in Properties
    @Value("${app.sendgrid.key}")
    private String appkey;
    

    @Bean
    public SendGrid getSendGrid(){
        return new SendGrid("appkey");
    }

}
