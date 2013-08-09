package com.ubc.ca.test;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.ubc.ca.service.ItemService;
import com.ubc.ca.service.ProductService;

public class ItemTest {

	float price = Float.parseFloat("10.50");
	@Test
	public void addItemTest() {
		ItemService is = new ItemService();
		try {
			is.UpdateItem("35", 7, price);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void setDelivereDate() {
		ProductService ps = new ProductService();
		try {
			ps.setDeliveryDate(new java.sql.Date(System.currentTimeMillis()), 10);
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
