package com.hungerbox.menu_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungerbox.menu_service.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
