package com.hungerbox.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungerbox.payment_service.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
