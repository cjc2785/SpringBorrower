package com.ss.lms.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbl_book_loans", schema="library")
public class BookLoans {
	
	@EmbeddedId
	private BookLoansId id;
	
	@ManyToOne
	@JoinColumn(name="bookId")
	@MapsId("bookId")
	private Book book;
	
	@ManyToOne
	@JoinColumn(name="branchId")
	@MapsId("branchId")
	private LibraryBranch branch;
	
	@ManyToOne
	@JoinColumn(name="bookId")
	@MapsId("bookId")
	private Borrower borroewer;

	
	@Temporal(TemporalType.DATE)
	@Column
	private Date dateOut;
	
	@Temporal(TemporalType.DATE)
	@Column
	private Date dueDate;
	
	public BookLoans() { }

	public BookLoans(BookLoansId id, Book book, LibraryBranch branch, Borrower borroewer, Date dateOut, Date dueDate) {
		this.id = id;
		this.book = book;
		this.branch = branch;
		this.borroewer = borroewer;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
	}

	public BookLoansId getId() {
		return id;
	}

	public void setId(BookLoansId id) {
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

	public Borrower getBorroewer() {
		return borroewer;
	}

	public void setBorroewer(Borrower borroewer) {
		this.borroewer = borroewer;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
