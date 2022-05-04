package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.util.MapperUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestimonialMapper implements IMapper<Testimonial, TestimonialDto> {

    @Override
    public Testimonial toEntity(TestimonialDto dto) {
        return Testimonial.builder()
                .name(dto.getName())
                .content(dto.getContent())
                .image(dto.getImage())
                .build();
    }

    @Override
    public Testimonial toEntity(Long id, TestimonialDto dto) {
        return Testimonial.builder()
                .id(dto.getId())
                .name(dto.getName())
                .content(dto.getContent())
                .image(dto.getImage())
                .build();
    }

    @Override
    public TestimonialDto toDto(Testimonial entity) {
        return TestimonialDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .content(entity.getContent())
                .image(entity.getImage())
                .created(entity.getCreated())
                .updated(entity.getUpdated())
                .build();
    }

    @Override
    public List<TestimonialDto> toDtoList(List<Testimonial> list) {
        return MapperUtil.streamListNonNull(list, this::toDto);
    }

    public Testimonial updateEntity(Testimonial entity, TestimonialDto dto) {
        entity.setContent(dto.getContent());
        entity.setName(dto.getName());
        entity.setImage(dto.getImage());
        entity.setUpdated(dto.getUpdated());
        entity.setCreated(dto.getCreated());
        return entity;
    }
}
