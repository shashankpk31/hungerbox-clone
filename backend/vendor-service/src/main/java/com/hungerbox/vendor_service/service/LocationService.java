package com.hungerbox.vendor_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hungerbox.vendor_service.dto.request.LocationRequest;
import com.hungerbox.vendor_service.dto.request.OfficeRequest;
import com.hungerbox.vendor_service.dto.response.LocationResponse;
import com.hungerbox.vendor_service.dto.response.OfficeResponse;
import com.hungerbox.vendor_service.entity.Location;
import com.hungerbox.vendor_service.entity.Office;
import com.hungerbox.vendor_service.mapper.LocationMapper;
import com.hungerbox.vendor_service.mapper.OfficeMapper;
import com.hungerbox.vendor_service.repository.LocationRepository;
import com.hungerbox.vendor_service.repository.OfficeRepository;
import com.hungerbox.vendor_service.util.UserContext;

@Service
public class LocationService {
	private final LocationRepository locationRepository;
	private final OfficeRepository officeRepository;
	private final LocationMapper locationMapper;
	private final OfficeMapper officeMapper;

	public LocationService(LocationRepository locationRepository, OfficeRepository officeRepository,
			LocationMapper locationMapper, OfficeMapper officeMapper) {
		this.locationRepository = locationRepository;
		this.officeRepository = officeRepository;
		this.locationMapper = locationMapper;
		this.officeMapper = officeMapper;
	}

	public LocationResponse saveLocation(LocationRequest locationReq) {
		Long authenticatedOrgId = UserContext.get().orgId();
		Location location = locationMapper.toEntity(locationReq);
		location.setOrgId(authenticatedOrgId);
		locationRepository.save(location);
		return locationMapper.toResponse(location);
	}

	public OfficeResponse createOffice(OfficeRequest officeReq) {
		Office office = officeMapper.toEntity(officeReq);
		officeRepository.save(office);
		return officeMapper.toResponse(office);
	}

	public List<LocationResponse> findByOrgId(Long orgId) {
		List<Location> locations = locationRepository.findByOrgId(orgId);
		return locationMapper.toResponse(locations);
	}

	public List<OfficeResponse> getOfficesByLocation(Long locationId) {
		List<Office> offices = officeRepository.findByLocationId(locationId);
		return officeMapper.toResponse(offices);
	}
}
