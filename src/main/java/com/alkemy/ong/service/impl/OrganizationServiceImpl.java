package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.OrganizationDetailedDto;
import com.alkemy.ong.dto.OrganizationSocialAddressesDto;
import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.type.OrganizationDtoType;
import com.alkemy.ong.exception.ParamNotFound;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.IOrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrganizationServiceImpl implements IOrganizationService {

    private final OrganizationMapper mapper;
    private final OrganizationRepository repository;
    private final MessageSource messageSource;

    @Override
    public Object getOrganizationPublicData() {
        return mapper.mappingOrganizationDto(repository.getOrganizationPublicData())
                .generateDto(OrganizationDtoType.PUBLIC_DATA, messageSource);
    }

    @Override
    public OrganizationDto getOrganizationDto(Long id){
        Organization idOrg = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category", "id", id));;
        OrganizationDto dtoOrg = mapper.toDto(idOrg);
        return dtoOrg;
    }

    public OrganizationDetailedDto update(OrganizationDetailedDto dto) {

        Optional<Organization> model = repository.findAll().stream().findFirst();
        if(!model.isPresent()){
            throw new ParamNotFound(messageSource.getMessage("error.organization",null, Locale.US));
        }

        mapper.update(model.get(),dto);
        Organization modelSaved = repository.save(model.get());
        OrganizationDetailedDto resultDto = mapper.OrganizationModel2OrganizationDetailedDto(modelSaved);

        return resultDto;
    }
}
