package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlidesUpdateDTO;

import org.springframework.http.ResponseEntity;


public interface ISlideService {
    Object getSlideDetail(Long id);
    ResponseEntity<?> updateSlide(Long id, SlidesUpdateDTO slideUpdate);
    void deleteSlice(Long id);
}
