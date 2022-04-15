package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}





