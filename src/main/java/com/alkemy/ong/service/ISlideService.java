package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlideResponseDto;

import java.util.List;

public interface ISlideService {

    Object getSlideDetail(Long id);

    List<SlideResponseDto> getAll();

    void deleteSlide(Long id);

}
