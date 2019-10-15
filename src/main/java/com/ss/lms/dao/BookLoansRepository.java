package com.ss.lms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.lms.model.*;

public interface BookLoansRepository extends JpaRepository<BookLoans, BookLoansId> {

	public List<BookLoans> findByIdCardNo(int cardNo);
	
	public List<BookLoans> findByIdBranchIdAndIdCardNo(
			int branchId, int cardNo);
}
