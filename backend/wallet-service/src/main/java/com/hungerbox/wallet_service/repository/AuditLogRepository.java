package com.hungerbox.wallet_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hungerbox.wallet_service.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
