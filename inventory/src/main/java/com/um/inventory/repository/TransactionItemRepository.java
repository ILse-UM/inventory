package com.um.inventory.repository;

import com.um.inventory.model.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, Integer> {
}
