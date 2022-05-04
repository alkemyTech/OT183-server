package com.alkemy.ong.repository;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query(value = "SELECT new com.alkemy.ong.dto.OrganizationDto(o.name, o.image, o.address, o.phone, o.facebook, o.linkedin, o.instagram) FROM Organization o")
    OrganizationDto getOrganizationPublicData();

}


