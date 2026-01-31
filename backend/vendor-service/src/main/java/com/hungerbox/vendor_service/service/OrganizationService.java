package com.hungerbox.vendor_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hungerbox.vendor_service.constant.ErrorMessageWarnConstant.Error;
import com.hungerbox.vendor_service.controller.OrganizationController;
import com.hungerbox.vendor_service.dto.request.OrganizationRequest;
import com.hungerbox.vendor_service.dto.response.OrganizationResponse;
import com.hungerbox.vendor_service.entity.Organization;
import com.hungerbox.vendor_service.mapper.OrganizationMapper;
import com.hungerbox.vendor_service.repository.OrganizationRepository;

import jakarta.transaction.Transactional;

@Service
public class OrganizationService {
	
	private static final Logger log = LoggerFactory.getLogger(OrganizationService.class);

	private final OrganizationRepository organizationRepository;
	private final OrganizationMapper organizationMapper;

	public OrganizationService(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper) {
		this.organizationRepository = organizationRepository;
		this.organizationMapper = organizationMapper;
	}

	public OrganizationResponse createOrganization(OrganizationRequest req) {
		Organization org = organizationMapper.toEntity(req);
		organizationRepository.save(org);
		return organizationMapper.toResponse(org);
	}

	public List<OrganizationResponse> findAllOrganisations() {
		List<Organization> organizations = organizationRepository.findAll();
		return organizationMapper.toResponse(organizations);
	}

	public OrganizationResponse findOrganizationById(Long id) {
		Organization org = organizationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(Error.ORG_NOT_FOUND.getMessage()));
		return organizationMapper.toResponse(org);
	}

	@Transactional
	public void deleteOrganization(Long id) {
		log.info("Rolling back organization creation for ID: {}", id);
	    if (organizationRepository.existsById(id)) {
	        organizationRepository.deleteById(id);
	    } else {
	    	log.warn("Attempted to delete organization {}, but it was already gone.", id);
	    }
	}

}
