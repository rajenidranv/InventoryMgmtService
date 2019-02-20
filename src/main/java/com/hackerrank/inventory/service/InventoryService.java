package com.hackerrank.inventory.service;

import com.hackerrank.inventory.model.Inventory;
import java.util.List;

public interface InventoryService {
    void deleteAllInventorys();
    void deleteInventoryById(Long id);
    Inventory createInventory(Inventory inventory);
    Inventory updateInventory(Inventory inventory,Long id);
    Inventory getInventoryById(Long id);
    List<Inventory> getAllInventorys();
}
