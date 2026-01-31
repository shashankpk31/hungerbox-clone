package com.hungerbox.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungerbox.order_service.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
