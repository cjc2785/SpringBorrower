package com.ss.lms.services;

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
import com.ss.lms.model.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class LibraryBranchServiceIT {


	@Autowired
	TestUtils dbUtil;
	
	@Autowired
	LibraryBranchService service;
	


	@BeforeEach
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
