package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.dto.SlideResponseCreationDto;
import org.springframework.http.ResponseEntity;
import com.alkemy.ong.dto.SlidesUpdateDTO;

public interface ISlideService {
    SlideResponseCreationDto createSlide(SlideRequestDto slideRequestDto);
    Object getSlideDetail(Long id);
    ResponseEntity<?> updateSlide(Long id, SlidesUpdateDTO slideUpdate);
    void deleteSlice(Long id);
}
