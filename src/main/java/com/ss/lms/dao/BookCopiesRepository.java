package com.ss.lms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ss.lms.model.*;

public interface BookCopiesRepository extends JpaRepository<BookCopies, BookCopiesId> {

	@Query("SELECT bc FROM BookCopies bc " 
			+ "WHERE bc.branch.branchId=:branch " 
			+ "AND bc.noOfCopies > 0")
	public List<BookCopies> getAvailableCopies(
			@Param("branch") int branchId);
}
