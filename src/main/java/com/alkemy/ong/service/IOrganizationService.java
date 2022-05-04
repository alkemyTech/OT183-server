package com.alkemy.ong.service;


import com.alkemy.ong.dto.OrganizationDetailedDto;

public interface IOrganizationService {

    public Object getOrganizationPublicData();

    OrganizationDetailedDto update(OrganizationDetailedDto dto);

}
