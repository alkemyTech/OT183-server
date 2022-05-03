package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.dto.SlideResponseCreationDto;

public interface ISlideService {

    Object getSlideDetail(Long id);

    void deleteSlice(Long id);

    SlideResponseCreationDto createSlide(SlideRequestDto slideRequestDto);
}
