package com.ubc.ca.model;

import com.ubc.ca.service.ReturnService;

public class Return {

	private int receiptid;
	private String upc;
	private int quantity;
	private int retid;

	public Return(int receiptid, String upc, int quantity) {
		this.retid = 0;
		this.receiptid = receiptid;
		this.upc = upc;
		this.quantity = quantity;
	}

	public int getReceiptid() {
		return receiptid;
	}

	public void setReceiptid(int receiptid) {
		this.receiptid = receiptid;
	}
	
	public int getRetid() {
		return retid;
	}

	public void setRetid(int retid) {
		this.retid = retid;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void saveReturn() throws Exception {
		ReturnService rs = new ReturnService();
		int retid = rs.checkAndProcessReturn(receiptid, quantity, upc);
		this.retid = retid;
	}

}
