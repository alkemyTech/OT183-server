package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private IContactService contactService;

    @PostMapping
    public ResponseEntity<?> newContact(@Valid @RequestBody ContactDto contactDto){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(contactService.addContact(contactDto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
