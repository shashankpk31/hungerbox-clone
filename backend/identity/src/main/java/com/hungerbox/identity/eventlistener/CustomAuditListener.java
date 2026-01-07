package com.hungerbox.identity.eventlistener;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;

import com.hungerbox.identity.entity.AuditLog;
import com.hungerbox.identity.repository.AuditLogRepository;
import com.hungerbox.identity.util.BeanUtil;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;

public class CustomAuditListener {

    @PostPersist
    public void onPostPersist(Object entity) {
        saveLog(entity, "INSERT");
    }

    @PostUpdate
    public void onPostUpdate(Object entity) {
        saveLog(entity, "UPDATE");
    }

    @PreRemove
    public void onPreRemove(Object entity) {
        saveLog(entity, "DELETE");
    }

    private void saveLog(Object entity, String action) {
        try {
            AuditLogRepository repo = BeanUtil.getBean(AuditLogRepository.class);
            
            AuditLog log = new AuditLog();
            log.setEntityName(entity.getClass().getSimpleName());
            log.setAction(action);
            log.setTimestamp(LocalDateTime.now());
            
            log.setEntityId(extractId(entity));
            
            String currentUser = "SYSTEM";
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                currentUser = auth.getName();
            }
            log.setChangedBy(currentUser);

            repo.save(log);
        } catch (Exception e) {
            System.err.println("Failed to log audit: " + e.getMessage());
        }
    }

    private String extractId(Object entity) {
        try {
            var field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            return String.valueOf(field.get(entity));
        } catch (Exception e) {
            return "N/A";
        }
    }
}
