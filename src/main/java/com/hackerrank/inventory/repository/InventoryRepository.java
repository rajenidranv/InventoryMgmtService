package com.hackerrank.inventory.repository;

import com.hackerrank.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("InventoryRepository")
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	

}
