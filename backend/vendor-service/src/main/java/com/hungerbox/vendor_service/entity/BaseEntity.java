package com.hungerbox.vendor_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hungerbox.vendor_service.eventlistener.CustomAuditListener;
import com.hungerbox.vendor_service.util.UserContext;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners({ AuditingEntityListener.class, CustomAuditListener.class })
@SQLRestriction("deleted = false")
public abstract class BaseEntity {

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime updatedAt;

	@CreatedBy
	@Column(nullable = false, updatable = false)
	private String createdBy;

	@LastModifiedBy
	@Column(insertable = false)
	private String updatedBy;

	@Column(nullable = false)
	private boolean deleted = false;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@PrePersist
	public void onPrePersist() {
		this.createdAt = LocalDateTime.now();
		this.createdBy = UserContext.get() != null ? UserContext.get().username() : "SYSTEM";
	}

	@PreUpdate
	public void onPreUpdate() {
		this.updatedAt = LocalDateTime.now();
		this.updatedBy = UserContext.get() != null ? UserContext.get().username() : "SYSTEM";
	}

	@PreRemove
	public void onPreRemove() {
		this.deleted = true;
		this.updatedAt = LocalDateTime.now();
		this.updatedBy = UserContext.get() != null ? UserContext.get().username() : "SYSTEM";
	}
}