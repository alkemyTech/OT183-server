package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.service.impl.SlideServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("slides")
public class SlideController {

    private final SlideServiceImpl service;

    @PostMapping
    public ResponseEntity<SlideResponseDto> createSlide(@Valid @RequestBody SlideRequestDto slideRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createSlide(slideRequestDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSlideData(@PathVariable Long id) {
        return new ResponseEntity<>(service.getSlideDetail(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteSlice(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
