package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ContactBasicDto;
import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Service
public class ContactServiceImpl implements IContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public void addContact(ContactDto contactDto) {
        Contact entity = contactMapper.contactDto2contactEntity(contactDto);
        contactRepository.save(entity);
    }

    @Override
    public List<ContactBasicDto> listContacts() {
        List<ContactBasicDto> result = contactMapper
                .contactEntityList2ContactBasicDtoList(contactRepository.findAll());
        if (result.isEmpty()){
            throw new NullListException(messageSource.getMessage("error.null_list", null, Locale.US));
        }
        return result;
    }
}
