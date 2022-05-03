package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.SlideResponseDto;
import com.alkemy.ong.dto.type.SlideDtoType;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.ISlideService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
                                () -> new EntityNotFoundException("Slide", "id", id)
                        )
        ).generateDto(SlideDtoType.DETAILED, messageSource);
    }

    @Override
    @Transactional
    public List<SlideResponseDto> getAll() {
        List<Slide> slideList = repository.findAll(
                Sort.by(Sort.Direction.ASC, "position"));
        if (slideList.isEmpty()) {
            throw new NullListException(messageSource.getMessage("error.null_list", null, Locale.US));
        }
        return mapper.toDtoResponseList(slideList);
    }

    public void deleteSlide(Long id) {

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Slide", "id", id);
        }

        repository.deleteById(id);
    }
}
