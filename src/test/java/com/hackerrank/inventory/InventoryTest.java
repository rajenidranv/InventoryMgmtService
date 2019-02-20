package com.hackerrank.inventory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.inventory.model.Inventory;



@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryTest {

	@Autowired
	private WebApplicationContext context;
	private Inventory  inventory;
	private MockMvc mvc;
	private String path="/Item";

	
	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	
	
	//view Inventorys
		@Test
		public void getInventoryTest() throws Exception {

		
			
			 ResultActions resultActions = mvc.perform(get(path));
	         MockHttpServletResponse mockResponse = resultActions.andReturn()
	                 .getResponse();
	         
	         
	        if( mockResponse.getStatus()==200) {       	 
	        	resultActions.andExpect(jsonPath("$").isNotEmpty())
	    		.andExpect(jsonPath("$[0].skuId").isNumber())
	    		.andExpect(jsonPath("$[0].inventoryName").isString())
	    		.andExpect(jsonPath("$[0].contactNumber").isNumber());
	        	
	        	
	         }else {
	        	 
	        	 resultActions.andExpect(status().isNotFound());
	        	}
	        	 
	        	 
	         }
			
	
	@Test
	public void getInventoryInvalidTest() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get(path+"/4565").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
		.andExpect(content().string("No Item with given id found."));
	}

	
	@Test
public void addInventoryTest() throws Exception {
		
		inventory = new Inventory();
		inventory.setSkuId(Long.valueOf(1001));
		inventory.setProductName("AXON");
		inventory.setProductLabel("AXL");
		inventory.setMinQtyReq(1);
		inventory.setInventoryOnHand(10);
		inventory.setPrice((double)20.00);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(path)
				.content(toJson(inventory))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.skuId").isNumber()).andReturn();
		
		JSONObject json = new JSONObject(result.getResponse().getContentAsString()); 
		
		
		
		mvc.perform(MockMvcRequestBuilders.get(path+"/"+json.get("skuId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$").isNotEmpty())
		.andExpect(jsonPath("$.skuId").value(json.get("skuId")))
		.andExpect(jsonPath("$.productName").value("AXON"));
		
		
		
		
		mvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$").isNotEmpty())
		.andExpect(jsonPath("$[0].skuId").isNumber())
		.andExpect(jsonPath("$[0].productName").isString());
		
		mvc.perform(MockMvcRequestBuilders.post(path)
				.content(toJson(inventory))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().string("Item with same id exists."));
	}
	
	@Test
	public void updateInventoryTest() throws Exception {

		inventory = new Inventory();
		inventory.setSkuId(Long.valueOf(1001));
		inventory.setProductName("AXON");
		inventory.setProductLabel("AXL");
		inventory.setMinQtyReq(1);
		inventory.setInventoryOnHand(10);
		inventory.setPrice((double)20.00);
		
		mvc.perform(MockMvcRequestBuilders.put(path+"/1001").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(inventory))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.skuId").isNumber()).andReturn();
		
		mvc.perform(MockMvcRequestBuilders.put(path+"/1005").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(inventory))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
		.andExpect(content().string("Item with the id not exists."));
		
		mvc.perform(MockMvcRequestBuilders.delete(path+"/1001").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.delete(path+"/1002").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
		.andExpect(content().string("Item with the id not exists."));
	}
	
	
	@Test
	public void deleteAllInventoryTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete(path).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
		
	}
	
	
	
	 private byte[] toJson(Object r) throws Exception {
	        ObjectMapper map = new ObjectMapper();
	        return map.writeValueAsString(r).getBytes();
	    }

}
