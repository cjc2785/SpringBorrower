package com.ss.lms.controller;

import com.ss.lms.services.*;
import com.ss.lms.exceptions.*;
import com.ss.lms.model.*;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/lms/borrower/")
public class BorrowerController {

	@Autowired
	private BookLoanService loanService;

	@Autowired
	private LibraryBranchService branchService;

	@Autowired
	private BookCopiesService copiesService;


	//Get all branches
	@GetMapping(
			value="branches",
			produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<LibraryBranch> getBranches() {

		List<LibraryBranch> branches = branchService.findAll();
		return branches;
	}

	//Get books with at least 1 copy available at branch
	@GetMapping(
			value="branches/{branchId}/books",
			produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Book> getBooks(@PathVariable("branchId") int branchId) throws EntityDoesNotExistException  {


		List<Book> books = copiesService.getAvailableByBranch(branchId)
				.stream()
				.map(BookCopies::getBook)
				.collect(Collectors.toList());

		return books;
	}

	//Get branches where borrower has at least 1 book checked out
	@GetMapping(
			value="borrowers/{cardNo}/branches",
			produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<LibraryBranch> getBranches(@PathVariable("cardNo") int cardNo) throws EntityDoesNotExistException {

		List<LibraryBranch> branches = loanService.getBranchesByCardNo(cardNo);

		return branches;
	}



	//Get all books checked out by borrower at library
	@GetMapping(
			value="borrowers/{cardNo}/branches/{branchId}/books",
			produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Book> getBooks(
			@PathVariable("cardNo") int cardNo,
			@PathVariable("branchId") int branchId) throws EntityDoesNotExistException {

		List<Book> books = loanService.getBooksByBranchAndCardNo(branchId, cardNo);
		return books;
	} 

	//Check out a book
	//Creates the outDate & dueDate
	@PostMapping("borrowers/{cardNo}/branches/{branchId}/books/{bookId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void insertLoan(
			@PathVariable("cardNo") int cardNo,
			@PathVariable("branchId") int branchId,
			@PathVariable("bookId") int bookId) throws EntityDoesNotExistException {


		BookLoansId loanId = new BookLoansId();

		loanId.setBookId(bookId);
		loanId.setBranchId(branchId);
		loanId.setCardNo(cardNo);


		loanService.insert(loanId);
	} 


	//Return a book
	//Sends a 404 if the loan does not exist
	@DeleteMapping("borrowers/{cardNo}/branches/{branchId}/books/{bookId}")
	public void deleteLoan(
			@PathVariable("cardNo") int cardNo,
			@PathVariable("branchId") int branchId,
			@PathVariable("bookId") int bookId) throws EntityDoesNotExistException {

		BookLoansId loanId = new BookLoansId();

		loanId.setBookId(bookId);
		loanId.setBranchId(branchId);
		loanId.setCardNo(cardNo);


		loanService.delete(loanId);
	} 
}