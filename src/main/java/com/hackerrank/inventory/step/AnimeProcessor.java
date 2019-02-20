package com.hackerrank.inventory.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.hackerrank.inventory.dto.InventoryDTO;

public class AnimeProcessor implements ItemProcessor<InventoryDTO, InventoryDTO> {
	
    private static final Logger log = LoggerFactory.getLogger(AnimeProcessor.class);
    
    @Override
    public InventoryDTO process(final InventoryDTO inventoryDTO) throws Exception {
    	
    	final Long id = inventoryDTO.getSkuId();
        final String productName = inventoryDTO.getProductName();
        final String productLabel = inventoryDTO.getProductLabel();
        final int minQty  =inventoryDTO.getMinQtyReq();
        final int inventoryOnHand=inventoryDTO.getInventoryOnHand();
        final double price=inventoryDTO.getPrice();

        final InventoryDTO transformedInventoryDTO = new InventoryDTO(id, productName, productLabel,minQty,inventoryOnHand,price);

        log.info("Converting (" + inventoryDTO + ") into (" + transformedInventoryDTO + ")");

        return transformedInventoryDTO;
    }

}
