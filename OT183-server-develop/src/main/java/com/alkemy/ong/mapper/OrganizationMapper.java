package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.util.MapperUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganizationMapper implements IMapper<Organization, OrganizationDto> {

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
}
