package com.hungerbox.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hungerbox.identity.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
