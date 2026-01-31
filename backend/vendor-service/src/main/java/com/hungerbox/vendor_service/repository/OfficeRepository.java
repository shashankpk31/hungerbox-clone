package com.hungerbox.vendor_service.repository;

import com.hungerbox.vendor_service.entity.Office;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {

	List<Office> findByLocationId(Long locationId);
}
