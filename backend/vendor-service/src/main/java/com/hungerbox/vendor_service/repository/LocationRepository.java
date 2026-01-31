package com.hungerbox.vendor_service.repository;

import com.hungerbox.vendor_service.entity.Location;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

	List<Location> findByOrgId(Long orgId);
}
