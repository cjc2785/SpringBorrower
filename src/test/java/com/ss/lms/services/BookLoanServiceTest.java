package com.ss.lms.services;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ss.lms.TestUtils;
import com.ss.lms.exceptions.DuplicateIdException;
import com.ss.lms.exceptions.EntityDoesNotExistException;
import com.ss.lms.model.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookLoanServiceTest {


	@Autowired
	TestUtils dbUtil;
	
	@Autowired
	BookLoanService service;
	
	@Autowired
	BookCopiesService copiesService;

	@Before
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
		
		List<Integer> expectedBooks = List.of(1, 2, 3);
		List<Integer> expectedBranches = List.of(3, 3, 3);
		
		
		assertEquals(3, actualLoanCount);
		
		assertTrue(actualBooks.containsAll(expectedBooks));
		assertTrue(actualBranches.containsAll(expectedBranches));
	}

	@Test
	public void insertShouldAddALoan() throws EntityDoesNotExistException, DuplicateIdException {
		
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
	public void insertShouldDecrementNoOfCopies() throws EntityDoesNotExistException, DuplicateIdException {
		
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
	public void insertShouldThrowDuplicateIdIfLoanExists()  {
		
		int danCardNo = 2;
		int dcBranch = 3;
		int canadaBook = 2;
		
		BookLoansId loanId = new BookLoansId(
				canadaBook,
				dcBranch,
				danCardNo);

		
		assertThrows(DuplicateIdException.class, () -> {
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
		
		
		
		boolean actual = service.get(loanId).isEmpty();
		
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
