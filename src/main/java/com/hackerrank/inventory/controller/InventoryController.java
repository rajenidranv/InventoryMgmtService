package com.hackerrank.inventory.controller;

import com.hackerrank.inventory.exception.BadResourceRequestException;
import com.hackerrank.inventory.exception.NoSuchResourceFoundException;
import com.hackerrank.inventory.model.Inventory;
import com.hackerrank.inventory.service.InventoryService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(value = "/Item", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createNewInventory(@RequestBody @Valid Inventory inventory) throws Exception{
       
    	ResponseEntity<Object>  response = null;
        try{
            response = new ResponseEntity<Object>(inventoryService.createInventory(inventory),HttpStatus.CREATED);
        }
        catch (BadResourceRequestException bex) {
            response = new ResponseEntity<Object>(bex.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return response;

    }
    
    @RequestMapping(value = "/Item/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    
     public ResponseEntity<Object>  createExistingInventory(@RequestBody @Valid Inventory inventory,@PathVariable Long id) {
    	ResponseEntity<Object>  response = null;
        try{
            response = new ResponseEntity<Object>(inventoryService.updateInventory(inventory, id),HttpStatus.OK);
        }
        catch (NoSuchResourceFoundException bex) {
            response = new ResponseEntity<Object>(bex.getMessage(),HttpStatus.NOT_FOUND);
        }
        return response;
    }
    
    @RequestMapping(value = "/Item", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllInventorys() {
    	inventoryService.deleteAllInventorys();
    }

    @RequestMapping(value = "/Item/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteInventoryById(@PathVariable Long id) {
    	ResponseEntity<Object>  response = null;
        try{
        	inventoryService.deleteInventoryById(id);
            response = new ResponseEntity<Object>(HttpStatus.OK);
        }
        catch (NoSuchResourceFoundException bex) {
            response = new ResponseEntity<Object>(bex.getMessage(),HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @RequestMapping(value = "/Item", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllInventorys() {
   	 ResponseEntity<Object> response = null;
   		try{
   			response = new ResponseEntity<Object>(inventoryService.getAllInventorys(), HttpStatus.OK);
   		}
   		catch(NoSuchResourceFoundException bex){
   			response = new ResponseEntity<Object>(bex.getMessage(),HttpStatus.NOT_FOUND);
   		}
   		return response;

    }

    @RequestMapping(value = "/Item/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getInventoryById(@PathVariable Long id) {
    	ResponseEntity<Object>  response = null;
        try{
            response = new ResponseEntity<Object>(inventoryService.getInventoryById(id),HttpStatus.OK);
        }
        catch (NoSuchResourceFoundException bex) {
            response = new ResponseEntity<Object>(bex.getMessage(),HttpStatus.NOT_FOUND);
        }
        return response;
         
        
        
    }
}
