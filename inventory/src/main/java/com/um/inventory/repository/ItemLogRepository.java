package com.um.inventory.repository;

import com.um.inventory.model.ItemLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemLogRepository extends JpaRepository<ItemLog, Integer> {
}
