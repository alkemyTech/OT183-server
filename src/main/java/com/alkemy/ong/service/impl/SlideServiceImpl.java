package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.type.SlideDtoType;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.ISlideService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@AllArgsConstructor
@Service
public class SlideServiceImpl implements ISlideService {

    private final SlideRepository repository;
    private final SlideMapper mapper;
    private final MessageSource messageSource;

    @Override
    public Object getSlideDetail(Long id) {
        return mapper.toDto(
                repository.findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException(
                                        messageSource.getMessage(
                                                "error.entity_not_found",
                                                null,
                                                Locale.US)
                                )
                        )
        ).generateDto(SlideDtoType.DETAILED, messageSource);
    }
}
