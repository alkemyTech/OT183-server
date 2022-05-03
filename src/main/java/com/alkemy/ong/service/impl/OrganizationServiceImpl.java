package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.OrganizationSocialAddressesDto;
import com.alkemy.ong.dto.type.OrganizationDtoType;
import com.alkemy.ong.exception.ParamNotFound;
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

    public Object getOrganizationPublicData() {
        return repository.getOrganizationPublicData()
                .generateDto(OrganizationDtoType.PUBLIC_DATA, messageSource);
    }

    public OrganizationSocialAddressesDto putSocialAddres(Long id, OrganizationSocialAddressesDto dto) {

        Optional<Organization> model = repository.findById(id);
        if(!model.isPresent()){
            throw new ParamNotFound(messageSource.getMessage("error.id",null, Locale.US));
        }

        mapper.putSocialAdressesDto(model.get(),dto);
        Organization modelSaved = repository.save(model.get());
        OrganizationSocialAddressesDto resultDto = mapper.OrganizationModel2OrganizationSocialAddressesDto(modelSaved);

        return resultDto;
    }
}
