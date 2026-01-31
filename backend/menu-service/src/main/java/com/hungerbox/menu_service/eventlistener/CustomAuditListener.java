package com.hungerbox.menu_service.eventlistener;

import java.time.LocalDateTime;


import com.hungerbox.menu_service.util.BeanUtil;
import com.hungerbox.menu_service.util.UserContext;
import com.hungerbox.menu_service.entity.AuditLog;
import com.hungerbox.menu_service.repository.AuditLogRepository;

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
            
            log.setChangedBy(UserContext.get().username());

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
