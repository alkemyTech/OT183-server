package com.alkemy.ong.service;

import com.alkemy.ong.dto.OrganizationSocialAddressesDto;

public interface IOrganizationService {

    public Object getOrganizationPublicData();

    OrganizationSocialAddressesDto putSocialAddres(Long id,OrganizationSocialAddressesDto dto);
}
