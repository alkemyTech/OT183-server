package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.util.MapperUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SlideMapper implements IMapper<Slide, SlideDto> {

    private static SlideMapper instance;

    private SlideMapper() {
    }

    public static SlideMapper getInstance() {
        if (instance == null) {
            instance = new SlideMapper();
        }
        return instance;
    }

    @Override
    public Slide toEntity(SlideDto dto) {
        OrganizationMapper organizationMapper = OrganizationMapper.getInstance();
        return Slide.builder()
                .imageUrl(dto.getImageUrl())
                .text(dto.getText())
                .position(dto.getPosition())
                .organization(organizationMapper.toEntity(dto.getOrganization()))
                .build();
    }

    @Override
    public Slide toEntity(Long id, SlideDto dto) {
        OrganizationMapper organizationMapper = OrganizationMapper.getInstance();
        return Slide.builder()
                .id(id)
                .imageUrl(dto.getImageUrl())
                .text(dto.getText())
                .position(dto.getPosition())
                .organization(organizationMapper.toEntity(dto.getOrganization()))
                .build();
    }

    @Override
    public SlideDto toDto(Slide entity) {
        OrganizationMapper organizationMapper = OrganizationMapper.getInstance();
        return SlideDto.builder()
                .id(entity.getId())
                .imageUrl(entity.getImageUrl())
                .text(entity.getText())
                .position(entity.getPosition())
                .organization(organizationMapper.toDto(entity.getOrganization()))
                .build();
    }

    @Override
    public List<SlideDto> toDtoList(List<Slide> list) {
        return MapperUtil.streamListNonNull(list, this::toDto);
    }

}
