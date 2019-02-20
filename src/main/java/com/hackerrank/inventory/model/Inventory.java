package com.hackerrank.inventory.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Inventory {
	
	 	@Id
	    private Long skuId;
	 	@Column(nullable=false)
	    @Size(max = 50)
	    private String productName;
	    @Column
	    private String productLabel;
	 	@Column
	    private int inventoryOnHand;
	 	@Column
	    private int minQtyReq;
	 	@Column
	    private double price;

		
		public Inventory () {
		    
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
