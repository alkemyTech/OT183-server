package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.dto.type.TestimonialDtoType;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.ITestimonialService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TestimonialServiceImpl implements ITestimonialService {

    private final TestimonialMapper mapper;
    private final TestimonialRepository repository;
    private final MessageSource messageSource;

    @Override
    public Object createTestimonial(TestimonialDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)))
                .generateDto(TestimonialDtoType.DETAILED, messageSource);
    }

    @Override
    public void deleteTestimonial(Long id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("Testimonial", "id", id);
        repository.deleteById(id);
    }

    @Override
    public Object updateTestimonialById(Long id, TestimonialDto testimonialDto) {
        Testimonial testimonial = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Testimonial", "id", id));
        Testimonial testimonialUpdate = repository.save(mapper.updateEntity(testimonial, testimonialDto));
        return mapper.toDto(testimonialUpdate)
                .generateDto(TestimonialDtoType.DETAILED, messageSource);
    }
}
