package com.alkemy.ong.repository;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query(value = "SELECT o FROM Organization o")
    Organization getOrganizationPublicData();

}


