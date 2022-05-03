package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.dto.SlideResponseDto;

public interface ISlideService {

    Object getSlideDetail(Long id);

    void deleteSlice(Long id);

    SlideResponseDto createSlide(SlideRequestDto slideRequestDto);
}
