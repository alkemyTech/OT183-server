package com.alkemy.ong.service;

import com.alkemy.ong.model.Organization;

public interface IOrganizationService {

    public Object getOrganizationPublicData();

    void update (Organization organization, Long OrganizationId);

}
