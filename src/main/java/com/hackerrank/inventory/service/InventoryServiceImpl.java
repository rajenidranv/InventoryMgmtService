package com.hackerrank.inventory.service;

import com.hackerrank.inventory.exception.BadResourceRequestException;
import com.hackerrank.inventory.exception.NoSuchResourceFoundException;
import com.hackerrank.inventory.model.Inventory;
import com.hackerrank.inventory.repository.InventoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("InventoryService")
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public void deleteAllInventorys() {
    	inventoryRepository.deleteAllInBatch();
    }

    @Override
    public void deleteInventoryById(Long id) {
    	 Inventory existingInventory = inventoryRepository.findOne(id);
    	 if (existingInventory == null) {
             throw new NoSuchResourceFoundException("Item with the id not exists.");
         }
    	 inventoryRepository.delete(id);
    }

    @Override
    public Inventory createInventory(Inventory inventory)  {
        Inventory existingInventory = inventoryRepository.findOne(inventory.getSkuId());

        if (existingInventory != null) {
            throw new BadResourceRequestException("Item with same id exists.");
        }

        Inventory savedInventory= inventoryRepository.save(inventory);
        return savedInventory;
    }

    @Override
    public Inventory updateInventory(Inventory inventory,Long id)  {
        Inventory existingInventory = inventoryRepository.findOne(id);
     
        if (existingInventory == null) {
            throw new NoSuchResourceFoundException("Item with the id not exists.");
        }
       
        Inventory savedInventory= inventoryRepository.save(inventory);
        return savedInventory;
    }
    
    @Override
    public Inventory getInventoryById(Long id) {
        Inventory Inventory = inventoryRepository.findOne(id);

        if (Inventory == null) {
            throw new NoSuchResourceFoundException("No Item with given id found.");
        }

        return Inventory;
    }

    @Override
    public List<Inventory> getAllInventorys() {
    	List<Inventory>  InventoryList =inventoryRepository.findAll();
    	 if (InventoryList.isEmpty()) {
             throw new NoSuchResourceFoundException("No Item with given id found.");
         }
    	 
    	 return InventoryList;

    }
}
