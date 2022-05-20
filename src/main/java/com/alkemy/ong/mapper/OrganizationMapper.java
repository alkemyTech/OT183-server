package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.OrganizationDetailedDto;
import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationSocialAddressesDto;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganizationMapper implements IMapper<Organization, OrganizationDto> {

    @Autowired
    private SlideMapper slideMapper;

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

    public void update(Organization model, OrganizationDetailedDto dto) {

        model.setFacebook(dto.getFacebook());
        model.setInstagram(dto.getInstagram());
        model.setLinkedin(dto.getLinkedin());
        model.setName(dto.getName());
        model.setImage(dto.getImage());
        model.setAddress(dto.getAddress());
        model.setPhone(dto.getPhone());
        model.setEmail(dto.getEmail());
        model.setWelcomeText(dto.getWelcomeText());
        model.setAboutUsText(dto.getAboutUsText());
    }

    public OrganizationDetailedDto OrganizationModel2OrganizationDetailedDto(Organization model) {

        OrganizationDetailedDto dto = new OrganizationDetailedDto();
        dto.setFacebook(model.getFacebook());
        dto.setLinkedin(model.getLinkedin());
        dto.setInstagram(model.getInstagram());
        dto.setName(model.getName());
        dto.setImage(model.getImage());
        dto.setAddress(model.getAddress());
        dto.setPhone(model.getPhone());
        dto.setEmail(model.getEmail());
        dto.setWelcomeText(model.getWelcomeText());
        dto.setAboutUsText(model.getAboutUsText());

        return dto;
    }

    public OrganizationDto mappingOrganizationDto(Organization entity) {
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
                .facebook(entity.getFacebook())
                .instagram(entity.getInstagram())
                .linkedin(entity.getLinkedin())
                .slides(slideMapper
                        .toDtoList(entity
                                .getSlides()
                                .stream()
                                .sorted(Comparator.comparing(Slide::getPosition))
                                .collect(Collectors.toList())))
                .build();
    }


}
