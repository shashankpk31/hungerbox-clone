package com.hungerbox.vendor_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hungerbox.vendor_service.constant.ErrorMessageWarnConstant.Error;
import com.hungerbox.vendor_service.dto.request.CafeteriaRequest;
import com.hungerbox.vendor_service.dto.response.CafeteriaResponse;
import com.hungerbox.vendor_service.entity.Cafeteria;
import com.hungerbox.vendor_service.mapper.CafeteriaMapper;
import com.hungerbox.vendor_service.repository.CafeteriaRepository;

@Service
public class CafeteriaService {
	private final CafeteriaRepository cafeteriaRepository;
	private final CafeteriaMapper cafeteriaMapper;

	public CafeteriaService(CafeteriaRepository cafeteriaRepository, CafeteriaMapper cafeteriaMapper) {
		this.cafeteriaRepository = cafeteriaRepository;
		this.cafeteriaMapper = cafeteriaMapper;
	}

	public CafeteriaResponse createCafeteria(CafeteriaRequest cafeteria) {
		Cafeteria newCafeteria = cafeteriaMapper.toEntity(cafeteria);
		cafeteriaRepository.save(newCafeteria);
		return cafeteriaMapper.toResponse(newCafeteria);
	}

	public List<CafeteriaResponse> findByOfficeId(Long officeId) {
		List<Cafeteria> cafeterias = cafeteriaRepository.findByOfficeId(officeId);
		return cafeteriaMapper.toResponse(cafeterias);
	}

	public CafeteriaResponse updateStatus(Long id, Boolean active) {
		Cafeteria cafeteria = cafeteriaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(Error.CAFE_NOT_FOUND.getMessage()));
		cafeteria.setIsActive(active);
		cafeteriaRepository.save(cafeteria);
		return cafeteriaMapper.toResponse(cafeteria);
	}

}
