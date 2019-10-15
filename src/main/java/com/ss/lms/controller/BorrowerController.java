package com.ss.lms.controller;

import com.ss.lms.services.*;
import com.ss.lms.exceptions.*;
import com.ss.lms.model.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


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
    @GetMapping("branches")
    public List<LibraryBranch> getBranches() {
    	try {
    	List<LibraryBranch> branches = branchService.findAll();
    	return branches;
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    //Get books with at least 1 copy available at branch
    @GetMapping("branches/{branchId}/books")
    public List<Book> getBooks(@PathVariable("branchId") int branchId) {
    	try {
    	
    		List<Book> books = copiesService.getAvailableByBranch(branchId)
    				.stream()
    				.map(BookCopies::getBook)
    				.collect(Collectors.toList());
    		
    		return books;
    	} catch (EntityDoesNotExistException e) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
    		throw new RuntimeException(e);
    	} 
    }
    
    //Get branches where borrower has at least 1 book checked out
    @GetMapping("borrowers/{cardNo}/branches")
    public List<LibraryBranch> getBranches(@PathVariable("cardNo") int cardNo) {
    	try {
    	
    		List<LibraryBranch> branches = loanService.getBranchesByCardNo(cardNo);
    		
    		return branches;
   
    	} catch (EntityDoesNotExistException e) {
    		
    		String entity = e.getEntity();
    		throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, 
					entity + " does not exist"
					);
		} catch (Exception e) {
    		throw new RuntimeException(e);
    	} 
    }
    
    
    
    //Get all books checked out by borrower at library
    @GetMapping("borrowers/{cardNo}/branches/{branchId}/books")
    public List<Book> getBooks(
    		@PathVariable("cardNo") int cardNo,
    		@PathVariable("branchId") int branchId) {
    	
    	try {
  
    		List<Book> books = loanService.getBooksByBranchAndCardNo(branchId, cardNo);
    		return books;
    		
    	} catch (EntityDoesNotExistException e) {
    		
    		String entity = e.getEntity();
    		throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, 
					entity + " does not exist"
					);
		}  catch (Exception e) {
    		throw new RuntimeException(e);
    	} 
    } 
    
    //Check out a book
    //Creates the outDate & dueDate
    @PostMapping("borrowers/{cardNo}/branches/{branchId}/books/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertLoan(
    		@PathVariable("cardNo") int cardNo,
    		@PathVariable("branchId") int branchId,
    		@PathVariable("bookId") int bookId) {
    	
    	try {

    		BookLoansId loanId = new BookLoansId();
    		
    		loanId.setBookId(bookId);
    		loanId.setBranchId(branchId);
    		loanId.setCardNo(cardNo);
    		
    		
    		loanService.insert(loanId);
    		
    	} catch (EntityDoesNotExistException e) {
    		String entity = e.getEntity();
    		throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, 
					entity + " does not exist"
					);
    	} catch (DuplicateIdException e) {
       		throw new ResponseStatusException(
    					HttpStatus.NOT_FOUND, 
    					"loan exists"
    					);
    	}
    	catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    } 
    
    
    //Return a book
    //Sends a 404 if the loan does not exist
    @DeleteMapping("borrowers/{cardNo}/branches/{branchId}/books/{bookId}")
    public void deleteLoan(
    		@PathVariable("cardNo") int cardNo,
    		@PathVariable("branchId") int branchId,
    		@PathVariable("bookId") int bookId) {
    	
    	try {
    	
    		BookLoansId loanId = new BookLoansId();
    		
    		loanId.setBookId(bookId);
    		loanId.setBranchId(branchId);
    		loanId.setCardNo(cardNo);
    		
    		
    		loanService.delete(loanId);
    		
    	} catch (EntityDoesNotExistException e) {
    		//The loan does not exist
    		
    		throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, 
					"loan does not exist"
					);
    	} 
    	catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    } 
}