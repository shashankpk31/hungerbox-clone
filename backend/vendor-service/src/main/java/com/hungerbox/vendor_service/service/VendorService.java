package com.hungerbox.vendor_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hungerbox.vendor_service.dto.request.VendorRequest;
import com.hungerbox.vendor_service.dto.response.VendorResponse;
import com.hungerbox.vendor_service.entity.Vendor;
import com.hungerbox.vendor_service.mapper.VendorMapper;
import com.hungerbox.vendor_service.repository.VendorRepository;

@Service
public class VendorService {
	private final VendorRepository vendorRepository;
	private final VendorMapper vendorMapper;

	public VendorService(VendorRepository vendorRepository, VendorMapper vendorMapper) {
		this.vendorRepository = vendorRepository;
		this.vendorMapper = vendorMapper;
	}

	public VendorResponse createVendor(VendorRequest req) {
		Vendor vendor = vendorMapper.toEntity(req);
		vendorRepository.save(vendor);
		return vendorMapper.toResponse(vendor);
	}

	public List<VendorResponse> getVendorsByCafeteria(Long cafeteriaId) {
		List<Vendor> vendors = vendorRepository.findByCafeteriaId(cafeteriaId);
		return vendorMapper.toResponse(vendors);
	}
}
