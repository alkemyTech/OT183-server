package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.model.Organization;


@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query(value = "SELECT new com.alkemy.ong.dto.OrganizationDto(o.name, o.image, o.address, o.phone) FROM Organization o")
    OrganizationDto getOrganizationPublicData();

}


