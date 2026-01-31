package com.hungerbox.vendor_service.repository;

import com.hungerbox.vendor_service.entity.Organization;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

	Optional<Organization> findByName(String string);
}
