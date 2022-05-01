package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.util.MapperUtil;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganizationMapper implements IMapper<Organization, OrganizationDto> {

    private static OrganizationMapper instance;

    private OrganizationMapper() {
    }

    public static OrganizationMapper getInstance() {
        if (instance == null) {
            instance = new OrganizationMapper();
        }
        return instance;
    }

    @Override
    public Organization toEntity(OrganizationDto dto) {
        return Organization.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .welcomeText(dto.getWelcomeText())
                .aboutUsText(dto.getAboutUsText())
                .build();
    }

    @Override
    public Organization toEntity(Long id, OrganizationDto dto) {
        return Organization.builder()
                .id(id)
                .name(dto.getName())
                .image(dto.getImage())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .welcomeText(dto.getWelcomeText())
                .aboutUsText(dto.getAboutUsText())
                .build();
    }

    @Override
    public OrganizationDto toDto(Organization entity) {
        return OrganizationDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .image(entity.getImage())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .welcomeText(entity.getWelcomeText())
                .aboutUsText(entity.getAboutUsText())
                .created(entity.getCreated())
                .updated(entity.getUpdated())
                .build();
    }

    @Override
    public List<OrganizationDto> toDtoList(List<Organization> list) {
        return MapperUtil.streamListNonNull(list, this::toDto);
    }

    public OrganizationDto mappingOrganizationDto(Organization entity) {
        SlideMapper slideMapper = SlideMapper.getInstance();
        return OrganizationDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .image(entity.getImage())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .welcomeText(entity.getWelcomeText())
                .aboutUsText(entity.getAboutUsText())
                .created(entity.getCreated())
                .updated(entity.getUpdated())
                .slides(slideMapper
                        .toDtoList(entity
                                .getSlides()
                                .stream()
                                .sorted(Comparator.comparing(Slide::getPosition))
                                .collect(Collectors.toList())))
                .build();
    }


}
