package com.ubc.ca.model;

public class Customer {
 //Customer Attributes
	private String cid;
	private String cname; 
	private String caddress;
	private String cpassword; 
	private String cphone; 
	
	public Customer(String cid, String cname, String cadd, String cpass, String cphone){
		this.cid = cid; 
		this.cname = cname; 
		this.caddress = cadd; 
		this.cpassword = cpass; 
		this.cphone = cphone; 
	} 
	
	
  public String getCID(){
	  return this.cid;
  }
  
  public String getCname(){
	  return this.cname;
  }
  
  public String getCaddress(){
	  return this.caddress;
  }
  
  public String getCpassword(){
	  return this.cpassword;
  }
  
  public String getCphone(){
	  return this.cphone;
  }

}
