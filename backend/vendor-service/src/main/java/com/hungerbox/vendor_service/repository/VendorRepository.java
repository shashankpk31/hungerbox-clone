package com.hungerbox.vendor_service.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hungerbox.vendor_service.entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

	List<Vendor> findByCafeteriaId(Long cafeteriaId);
}
