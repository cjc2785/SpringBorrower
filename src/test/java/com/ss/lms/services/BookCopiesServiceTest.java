package com.ss.lms.services;


import static org.junit.Assert.assertTrue;

import java.util.Arrays;
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
import com.ss.lms.exceptions.EntityDoesNotExistException;
import com.ss.lms.model.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookCopiesServiceTest {


	@Autowired
	TestUtils dbUtil;
	
	@Autowired
	BookCopiesService service;
	


	@Before
	public void setUp() {
		dbUtil.populateTestDb();
	}
	
	@Test
	public void getAvailableShouldReturnAllBooksWithAtLeast1CopyAtDcBranch() throws EntityDoesNotExistException {
		
		int dcBranchId = 3;
		
		List<BookCopies> actual = service.getAvailableByBranch(dcBranchId);
		
		
		List<Integer> actualIds = actual.stream()
				.map(BookCopies::getBook)
				.map(Book::getBookId)
				.collect(Collectors.toList());
	
		List<Integer> expected = Arrays.asList(1, 3, 4);
	
	
		assertTrue(actualIds.containsAll(expected));
	}
}
