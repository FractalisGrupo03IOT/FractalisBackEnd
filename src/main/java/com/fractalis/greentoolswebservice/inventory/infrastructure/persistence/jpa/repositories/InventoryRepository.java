package com.fractalis.greentoolswebservice.inventory.infrastructure.persistence.jpa.repositories;

import com.fractalis.greentoolswebservice.inventory.domain.model.aggregates.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByUserId(Long userId);
}