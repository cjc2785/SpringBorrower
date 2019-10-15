package com.ss.lms.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="tbl_book_copies", schema="library")
public class BookCopies {
	
	@EmbeddedId
	private BookCopiesId id;
	
	@ManyToOne
	@JoinColumn(name="bookId")
	@MapsId("bookId")
	private Book book;
	
	
	@ManyToOne
	@JoinColumn(name="bookId")
	@MapsId("branchId")
	private LibraryBranch branch;

	@Column
	private int noOfCopies;
	
	public BookCopies() { }

	public BookCopies(BookCopiesId id, Book book, LibraryBranch branch, int noOfCopies) {
		this.id = id;
		this.book = book;
		this.branch = branch;
		this.noOfCopies = noOfCopies;
	}

	public BookCopiesId getId() {
		return id;
	}

	public void setId(BookCopiesId id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LibraryBranch getBranch() {
		return branch;
	}

	public void setBranch(LibraryBranch branch) {
		this.branch = branch;
	}

	public int getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
}
