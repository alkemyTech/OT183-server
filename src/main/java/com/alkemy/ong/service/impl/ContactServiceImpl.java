package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ContactBasicDto;
import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Locale;

@Service
public class ContactServiceImpl implements IContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private MessageSource message;

    @Override
    @Transactional
    public ContactBasicDto addContact(ContactDto contactDto) {
        Contact contactEntity = contactMapper.contactDto2ContactEntity(contactDto);
        contactEntity = contactRepository.save(contactEntity);
        ContactBasicDto contactBasicDto = contactMapper.contactEntity2ContactBasicDto(contactEntity);
        return contactBasicDto;
    }


}
