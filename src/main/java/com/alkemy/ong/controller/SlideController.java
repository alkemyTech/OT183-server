package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.dto.SlideResponseCreationDto;
import com.alkemy.ong.dto.SlidesUpdateDTO;
import com.alkemy.ong.dto.response.UpdateSlidesDTO;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.service.impl.SlideServiceImpl;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;

import javax.validation.Valid;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("slides")
@Api(tags = "Slides")
public class SlideController {

    private final SlideServiceImpl service;
    private final MessageSource message;

    @PostMapping
    public ResponseEntity<SlideResponseCreationDto> createSlide(@Valid @RequestBody SlideRequestDto slideRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createSlide(slideRequestDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSlideData(@PathVariable Long id) {
        return new ResponseEntity<>(service.getSlideDetail(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteSlide(id);
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
