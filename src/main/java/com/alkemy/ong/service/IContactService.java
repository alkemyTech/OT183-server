package com.alkemy.ong.service;

import com.alkemy.ong.dto.ContactBasicDto;
import com.alkemy.ong.dto.ContactDto;

public interface IContactService {

    ContactBasicDto addContact(ContactDto contactDto);
}
