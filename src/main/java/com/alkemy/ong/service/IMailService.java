package com.alkemy.ong.service;

public interface IMailService {

    void sendEmailByRegistration(String emailReceiver, String firstName);
    void sendEmailByFormComplete(String emailReceiver);

}
