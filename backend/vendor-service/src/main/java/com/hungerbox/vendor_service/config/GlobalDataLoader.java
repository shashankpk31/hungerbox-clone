package com.hungerbox.vendor_service.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hungerbox.vendor_service.entity.Organization;
import com.hungerbox.vendor_service.repository.OrganizationRepository;

@Component
public class GlobalDataLoader implements CommandLineRunner {
	OrganizationRepository orgRepository;
	
	GlobalDataLoader(OrganizationRepository orgRepository){
		this.orgRepository=orgRepository;
	}
	
	@Override
    public void run(String... args) {
        if (orgRepository.findByName("HungerBox").isEmpty()) {
            Organization org = new Organization();
            org.setName("HungerBox");
            org.setDomain("hungerbox.com");
            org.setCreatedBy("SYSTEM");
            org.setCreatedAt(LocalDateTime.now());
            orgRepository.save(org);
        }
    }
}
