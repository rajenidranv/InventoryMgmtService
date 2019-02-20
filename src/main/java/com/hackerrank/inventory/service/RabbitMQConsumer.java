package com.hackerrank.inventory.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hackerrank.inventory.model.Inventory;
import com.hackerrank.inventory.service.InventoryService;

@Component
public class RabbitMQConsumer {
	
	
	 @Autowired
     InventoryService inventoryService;

	@RabbitListener(queues ="${hackerrank.rabbitmq.queue}")
    public void recievedMessage(String msg) {
          
       
        Inventory inventory =null;
		String[] tokens=msg.split("-");
		
		
		
		try {
			if( tokens!=null && !tokens[0].equalsIgnoreCase("")){
				inventory= inventoryService.getInventoryById(Long.valueOf(tokens[0]));
				}
			if( tokens!=null  && inventory!=null && !tokens[1].equalsIgnoreCase("")){
				inventory.setInventoryOnHand(inventory.getInventoryOnHand()-(Math.toIntExact(Long.valueOf(tokens[1]))));
				inventoryService.updateInventory(inventory, inventory.getSkuId());
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        
        
        
    }
}
