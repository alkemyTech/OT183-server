package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.type.OrganizationDtoType;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.IOrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrganizationServiceImpl implements IOrganizationService {

    private final OrganizationMapper mapper;
    private final OrganizationRepository repository;
    private final MessageSource messageSource;

    @Override
    public Object getOrganizationPublicData() {
        return repository.getOrganizationPublicData()
                .generateDto(OrganizationDtoType.PUBLIC_DATA, messageSource);
    }
}
