package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.dto.SlideResponseCreationDto;
import com.alkemy.ong.dto.SlideResponseDto;
import org.springframework.http.ResponseEntity;
import com.alkemy.ong.dto.SlidesUpdateDTO;

import javax.transaction.Transactional;
import java.util.List;

public interface ISlideService {
    @Transactional
    List<SlideResponseDto> getAll();

    SlideResponseCreationDto createSlide(SlideRequestDto slideRequestDto);
    Object getSlideDetail(Long id);
    ResponseEntity<?> updateSlide(Long id, SlidesUpdateDTO slideUpdate);
    void deleteSlide(Long id);
}
