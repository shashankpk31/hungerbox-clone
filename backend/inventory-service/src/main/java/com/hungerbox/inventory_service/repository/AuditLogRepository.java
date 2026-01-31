package com.hungerbox.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungerbox.inventory_service.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
