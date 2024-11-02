package com.fractalis.greentoolswebservice.inventory.application.internal.queryservices;
import com.fractalis.greentoolswebservice.account.domain.model.aggregates.User;
import com.fractalis.greentoolswebservice.inventory.domain.model.aggregates.Inventory;
import com.fractalis.greentoolswebservice.inventory.domain.services.InventoryCommandService;
import com.fractalis.greentoolswebservice.inventory.domain.services.InventoryQueryService;
import com.fractalis.greentoolswebservice.inventory.infrastructure.persistence.jpa.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryQueryServiceImpl implements InventoryQueryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryQueryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    @Override
    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public List<Inventory> getInventoryByUserId(Long userId) {
        return inventoryRepository.findByUserId(userId);
    }
}