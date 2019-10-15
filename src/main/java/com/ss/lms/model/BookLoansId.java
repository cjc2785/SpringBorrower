package com.ss.lms.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class BookLoansId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	private int bookId;
	private int branchId;
	private int cardNo;
	
	public BookLoansId() { } 
	
	public BookLoansId(int bookId, int branchId, int cardNo) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
	}
	
	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public int getCardNo() {
		return cardNo;
	}

	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	


	@Override
	public boolean equals(Object o) {
		
		boolean isInstance = o != null &&
				o instanceof BookLoansId;
		
		if(!isInstance) {
			return false;
		}
		
		BookLoansId x = (BookLoansId)o;
		
		return bookId == x.bookId && 
				branchId == x.branchId &&
				cardNo == x.cardNo;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bookId, branchId, cardNo);
	}
}
