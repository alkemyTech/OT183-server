package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.type.OrganizationDtoType;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.IOrganizationService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class OrganizationServiceImpl implements IOrganizationService {

    private final OrganizationMapper mapper;
    private final OrganizationRepository repository;

    @Override
    public Object getOrganizationPublicData() {
        return repository.getOrganizationPublicData().generateDto(OrganizationDtoType.PUBLIC_DATA);
    }

    @Override
    public void update (Organization organization, Long organizationId){
        Optional<Organization> org = this.repository.findById(organizationId);

        Organization organization1 = org.get();

        organization1.setName(organization.getName());
        organization1.setImage(organization.getImage());
        organization1.setAddress(organization.getAddress());
        organization1.setPhone(organization.getPhone());
        organization1.setEmail(organization.getEmail());
        organization1.setWelcomeText(organization.getWelcomeText());
        organization1.setAboutUsText(organization.getAboutUsText());

    }

}
