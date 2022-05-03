package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlidesUpdateDTO;
import com.alkemy.ong.dto.response.UpdateSlidesDTO;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.service.impl.SlideServiceImpl;
import lombok.AllArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("slides")
public class SlideController {

    private final SlideServiceImpl service;
    private final MessageSource message;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSlideData(@PathVariable Long id) {
        return new ResponseEntity<>(service.getSlideDetail(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteSlice(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSlide(@PathVariable("id") long id,@Valid @RequestBody SlidesUpdateDTO slideUpdate) throws EntityNotFoundException{
        try{
            return service.updateSlide(id, slideUpdate);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message.getMessage("data.not.found", null, Locale.US));
        } 
    }

}
