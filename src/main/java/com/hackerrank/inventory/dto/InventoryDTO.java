package com.hackerrank.inventory.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Size;

public class InventoryDTO {

 
    private Long skuId;
 	
    private String productName;
  
    private String productLabel;
 	
    private int inventoryOnHand;
 	
    private int minQtyReq;
 
    private double price;

	
	public InventoryDTO () {
	    
	}
    
	public InventoryDTO(Long skuId, String productName, String productLabel,int inventoryonHand,int minQtyReq,double price){
		this.skuId = skuId;
		this.productName = productName;
		this.productLabel = productLabel;
		this.inventoryOnHand = inventoryonHand;
		this.minQtyReq = minQtyReq;
		this.price = price;
		
	}
	

	
	   @Override
	    public String toString() {
		   return new org.apache.commons.lang.builder.ToStringBuilder(this)
				   .append("skuId", this.skuId)
				   .append("productName", this.productName)
				   .append("productLabel", this.productLabel)
				   .append("inventoryOnHand", this.inventoryOnHand)
				   .append("minQtyReq", this.minQtyReq)
				   .append("price", this.price)
				   .toString();
	   }


	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductLabel() {
		return productLabel;
	}

	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}

	public int getInventoryOnHand() {
		return inventoryOnHand;
	}

	public void setInventoryOnHand(int inventoryOnHand) {
		this.inventoryOnHand = inventoryOnHand;
	}

	public int getMinQtyReq() {
		return minQtyReq;
	}

	public void setMinQtyReq(int minQtyReq) {
		this.minQtyReq = minQtyReq;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
