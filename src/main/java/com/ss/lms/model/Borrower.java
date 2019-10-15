package com.ss.lms.model;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tbl_borrower")
public class Borrower {
	
	@Id
	@Column
	private int cardNo;
	
	@Column
	private String name;
	
	@Column
	private String address;
	
	@Column
	private String phone;
	
	public Borrower() {};
	
	public Borrower(int cardNumber, String name, String address, String phoneNumber) {
		this.cardNo = cardNumber;
		this.name = name;
		this.address = address;
		this.phone = phoneNumber;
		
	}

	public int getCardNo() {
		return cardNo;
	}

	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
