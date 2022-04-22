package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class ContactServiceImpl implements IContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactMapper contactMapper;

    @Override
    @Transactional
    public void addContact(ContactDto contactDto) {
        Contact entity = contactMapper.contactDto2contactEntity(contactDto);
        contactRepository.save(entity);
    }
}
