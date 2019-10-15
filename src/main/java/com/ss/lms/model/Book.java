package com.ss.lms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbl_book")
public class Book {
	
	@Id
	@Column
	private int bookId;
	
	@Column
	private String title;
	
	@OneToOne
	@JoinColumn(name="authId", referencedColumnName="authorId")
	private Author author;
	
	@OneToOne
	@JoinColumn(name="pubId", referencedColumnName="publisherId")
	private Publisher publisher;
	
	public Book() { }
	
	public Book(int bookId, String title, Author author, Publisher publisher) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
}
