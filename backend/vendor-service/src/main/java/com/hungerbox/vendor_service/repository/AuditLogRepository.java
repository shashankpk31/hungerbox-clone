package com.hungerbox.vendor_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungerbox.vendor_service.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
