package com.ss.lms.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class BookCopiesId implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private int bookId;
	private int branchId;
	
	public BookCopiesId() { }
	
	public BookCopiesId(int bookId, int branchId) {
		this.bookId = bookId;
		this.branchId = branchId;
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

	
	@Override
	public boolean equals(Object o) {
		
		boolean isInstance = o != null &&
				o instanceof BookCopiesId;
		
		if(!isInstance) {
			return false;
		}
		
		BookCopiesId x = (BookCopiesId)o;
		
		return bookId == x.bookId && 
				branchId == x.branchId;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bookId, branchId);
	}
}
