package com.hackerrank.inventory.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hackerrank.inventory.model.Inventory;
import com.hackerrank.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author rajen.venkatraman
 */
@Component
public class SchedulerInventoryCheck {

	@Autowired
	private InventoryRepository inventoryRepository;

	private static final Logger logger = LoggerFactory.getLogger(SchedulerInventoryCheck.class);

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Scheduled(fixedRate = 300000)
	public void scheduleTaskWithFixedRate() {
		logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
		List<Inventory> inventories = new ArrayList<Inventory>();
		inventoryRepository.findAll().forEach(e -> inventories.add(e));

		inventories.forEach(inventory -> {
			int inventoryOnHand = inventory.getInventoryOnHand();
			int minQty = inventory.getMinQtyReq();
			if (inventoryOnHand < minQty) {
				inventoryOnHand = inventoryOnHand + minQty + 10;
				inventory.setInventoryOnHand(inventoryOnHand);
				inventoryRepository.save(inventory);
			}
		});

	}

}