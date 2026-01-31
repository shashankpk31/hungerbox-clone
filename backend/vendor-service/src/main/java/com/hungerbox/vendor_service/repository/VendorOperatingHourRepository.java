package com.hungerbox.vendor_service.repository;

import com.hungerbox.vendor_service.entity.VendorOperatingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorOperatingHourRepository extends JpaRepository<VendorOperatingHour, Long> {
}
