package com.ubc.ca.model;

/**
 * 
 * @author Mohamed Makthum
 * This Model class contains attributes and fields associated with Item object used in shopping cart and Order transaction.
 */

public class Item {
	
	// Item object attributes 
	
	
	private String title;
	private String category;
	private String UPC;
	private int quantity;
	private float price;
	private float totalPrice;
	
	
	// Holds any error message to be displayed encountered during db transaction 
	
	private String errorMessage;
	
	public Item() {

	}
	
	public Item(String upc, String category, int quantity, float price) {
		this.category = category;
		this.UPC = upc;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = quantity * price;
	}
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	private String LeadSinger;
	public String getLeadSinger() {
		return LeadSinger;
	}
	public void setLeadSinger(String leadSinger) {
		LeadSinger = leadSinger;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getUPC() {
		return UPC;
	}
	public void setUPC(String uPC) {
		UPC = uPC;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	

}
