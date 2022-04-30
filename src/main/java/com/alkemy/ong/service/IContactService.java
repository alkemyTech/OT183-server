package com.alkemy.ong.service;

import com.alkemy.ong.dto.ContactBasicDto;
import com.alkemy.ong.dto.ContactDto;

import java.util.List;

public interface IContactService {

    void addContact(ContactDto contactDto);
    List<ContactBasicDto> listContacts();
}
