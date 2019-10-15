package com.ss.lms.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.lms.model.*;

public interface BorrowerRepository extends JpaRepository<Borrower, Integer> {

	public Optional<Borrower> findByCardNo(int cardNo);
	public void deleteByCardNo(int cardNo);
}
