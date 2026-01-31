package com.hungerbox.inventory_service.repository;

import com.hungerbox.inventory_service.entity.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockLogRepository extends JpaRepository<StockLog, Long> {
}
