package com.hungerbox.vendor_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hungerbox.vendor_service.entity.Cafeteria;

@Repository
public interface CafeteriaRepository extends JpaRepository<Cafeteria, Long> {

	List<Cafeteria> findByOfficeId(Long officeId);
}
