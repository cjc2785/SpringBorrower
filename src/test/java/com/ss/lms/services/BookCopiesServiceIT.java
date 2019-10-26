package com.ss.lms.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
public class BookCopiesServiceIT {


	@Autowired
	TestUtils dbUtil;
	
	@Autowired
	BookCopiesService service;
	


	@BeforeEach
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
