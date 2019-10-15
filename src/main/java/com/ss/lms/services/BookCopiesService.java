package com.ss.lms.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ss.lms.exceptions.*;
import com.ss.lms.dao.*;
import com.ss.lms.model.*;

@Service
public class BookCopiesService {
	
	@Autowired
	private LibraryBranchRepository branchRepo;

	
	@Autowired
	private BookCopiesRepository copiesRepo;
	
	
	public List<BookCopies> getAvailableByBranch(int branchId) 
			throws EntityDoesNotExistException {
		
		Optional<LibraryBranch> optBranch = branchRepo.findById(branchId);
		
		if(optBranch.isEmpty()) {
			
			throw new EntityDoesNotExistException("branch");
		}

		List<BookCopies> copiesList = copiesRepo.getAvailableCopies(branchId);
		
		return copiesList;
	}
}
