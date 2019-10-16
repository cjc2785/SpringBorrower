package com.ss.lms.services;


import static org.junit.Assert.assertTrue;


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
import com.ss.lms.model.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LibraryBranchServiceTest {


	@Autowired
	TestUtils dbUtil;
	
	@Autowired
	LibraryBranchService service;
	


	@Before
	public void setUp() {
		dbUtil.populateTestDb();
	}
	
	@Test
	public void getAllShouldReturnDcBranch() {
		
		int dcBranch = 3;
		
		
		
		List<LibraryBranch> actualBranches = service.findAll();
		
		
		
		List<Integer> actualIds = actualBranches.stream()
				.map(LibraryBranch::getBranchId)
				.collect(Collectors.toList());
		
		assertTrue(actualIds.contains(dcBranch));
	}
}
