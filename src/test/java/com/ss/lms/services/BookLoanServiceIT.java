package com.ss.lms.services;



import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.ss.lms.TestUtils;
import com.ss.lms.exceptions.EntityDoesNotExistException;
import com.ss.lms.model.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class BookLoanServiceIT {


	@Autowired
	TestUtils dbUtil;
	
	@Autowired
	BookLoanService service;
	
	@Autowired
	BookCopiesService copiesService;

	@BeforeEach
	public void setUp() {
		dbUtil.populateTestDb();
	}

	
	@Test
	public void getAllByBranchAndCardNoShouldReturnDansLoansFromDc() throws EntityDoesNotExistException {
		
		int dan = 2;
		int dc = 3;
		

		List<BookLoans> actual = service.getAllByBranchAndCardNo(dc, dan);
				
		
		List<Integer> actualBooks = actual.stream()
				.map(BookLoans::getBook)
				.map(Book::getBookId)
				.collect(Collectors.toList());
		
		
		List<Integer> actualBranches = actual.stream()
				.map(BookLoans::getBranch)
				.map(LibraryBranch::getBranchId)
				.collect(Collectors.toList());
		
		int actualLoanCount = actual.size();
		
		List<Integer> expectedBooks = Arrays.asList(1,2,3);
		List<Integer> expectedBranches = Arrays.asList(3);
		
		
		assertEquals(3, actualLoanCount);
		
		assertTrue(actualBooks.containsAll(expectedBooks));
		assertTrue(actualBranches.containsAll(expectedBranches));
	}

	@Test
	public void insertShouldAddALoan() throws EntityDoesNotExistException {
		
		int danCardNo = 2;
		int dcBranch = 3;
		int footballBook = 4;
		
		BookLoansId loanId = new BookLoansId(
				footballBook,
				dcBranch,
				danCardNo);
		
		
		service.insert(loanId);
		
		boolean actual = service.get(loanId).isPresent();
		
		assertTrue(actual);
	}
	
	@Test
	public void insertShouldDecrementNoOfCopies() throws EntityDoesNotExistException {
		
		int danCardNo = 2;
		int dcBranch = 3;
		int footballBook = 4;
		
		BookLoansId loanId = new BookLoansId(
				footballBook,
				dcBranch,
				danCardNo);

		
		
		service.insert(loanId);
		
		
		
		BookCopiesId copiesId = new BookCopiesId(
				footballBook, dcBranch);
		
		int actual = copiesService.get(copiesId)
				.get()
				.getNoOfCopies();
				
		assertEquals(2, actual);
	}
	
	@Test
	public void insertShouldThrowIfLoanExists()  {
		
		int danCardNo = 2;
		int dcBranch = 3;
		int canadaBook = 2;
		
		BookLoansId loanId = new BookLoansId(
				canadaBook,
				dcBranch,
				danCardNo);

		
		assertThrows(EntityDoesNotExistException.class, () -> {
			service.insert(loanId);
		});
	}
	
	
	@Test
	public void deleteShouldRemoveALoan() throws EntityDoesNotExistException {
		
		int danCardNo = 2;
		int dcBranch = 3;
		int canadaBook = 2;
		
		BookLoansId loanId = new BookLoansId(
				canadaBook,
				dcBranch,
				danCardNo);
		
		
		
		service.delete(loanId);
		
		
		
		boolean actual = !service.get(loanId).isPresent();
		
		assertTrue(actual);
	}
	
	@Test
	public void deleteShouldIncrementNoOfCopies() throws EntityDoesNotExistException {
		
		int danCardNo = 2;
		int dcBranch = 3;
		int canadaBook = 2;
		
		BookLoansId loanId = new BookLoansId(
				canadaBook,
				dcBranch,
				danCardNo);

		
		
		service.delete(loanId);
		
		
		
		BookCopiesId copiesId = new BookCopiesId(
				canadaBook, dcBranch);
		
		int actual = copiesService.get(copiesId)
				.get()
				.getNoOfCopies();
				
				
		assertEquals(1, actual);
	}
	
	@Test
	public void deleteShoulThrowEntityDoesNotExistWhenGivenDeletedLoan() throws EntityDoesNotExistException {
		
		int danCardNo = 2;
		int dcBranch = 3;
		int canadaBook = 2;
		
		BookLoansId loanId = new BookLoansId(
				canadaBook,
				dcBranch,
				danCardNo);

		
		//The loan should no longer exist after this delete
		service.delete(loanId);
		
		
		assertThrows(EntityDoesNotExistException.class, () -> {
			service.delete(loanId);
		});
	}
}
