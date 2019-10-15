package com.ss.lms.services;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.lms.exceptions.*;
import com.ss.lms.dao.*;
import com.ss.lms.model.*;

@Service
public class BookLoanService {
	
	//Utility function that can be passed to a filter
	static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> visited = new HashMap<>();
        return t -> visited.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	} 
	
	@Autowired
	private BorrowerRepository borrowerRepo;
	
	@Autowired
	private LibraryBranchRepository branchRepo;

	
	@Autowired
	private BookLoansRepository loanRepo;
	
	@Autowired
	private BookCopiesRepository copiesRepo;
	
	public List<Book> getBooksByBranchAndCardNo(int branchId, int cardNo) throws EntityDoesNotExistException {
		
		return getAllByBranchAndCardNo(branchId, cardNo)
				.stream()
				.map(BookLoans::getBook)
				.collect(Collectors.toList());
	}
	

	public List<BookLoans> getAllByBranchAndCardNo(int branchId, int cardNo) throws EntityDoesNotExistException {
		
		Optional<Borrower> optBorrower = borrowerRepo.findByCardNo(cardNo);
		
		if(optBorrower.isEmpty()) {
			throw new EntityDoesNotExistException("borrower");
		}
		

		Optional<LibraryBranch> optBranch = branchRepo.findById(branchId);
		
		if(optBranch.isEmpty()) {
			throw new EntityDoesNotExistException("branch");
		}
		
		return loanRepo.findByIdBranchIdAndIdCardNo(
				branchId, cardNo);
	}
	
	public Optional<BookLoans> get(Borrower borrower, LibraryBranch branch, Book book) 
		throws SQLException {
		
		BookLoansId loanId = new BookLoansId();
		
		loanId.setBookId(book.getBookId());
		loanId.setBranchId(branch.getBranchId());
		loanId.setCardNo(borrower.getCardNo());
		
		return loanRepo.findById(loanId);
	}
	
	public List<LibraryBranch> getBranchesByCardNo(int cardNo) 
			throws EntityDoesNotExistException {
		
		Optional<Borrower> optBorrower = borrowerRepo.findByCardNo(cardNo);
		
		if(optBorrower.isEmpty()) {
			throw new EntityDoesNotExistException("borrower");
		}
		
		return loanRepo.findByIdCardNo(cardNo)
				.stream()
				.map(BookLoans::getBranch)
				.filter(distinctByKey(LibraryBranch::getBranchId))
				.collect(Collectors.toList());
	}


	
	//Also decrements the noOfCopies 
	//Throws EntityDoesNotExistException if the 
	//  borrower, branch, or book does not exist
	//Throws DuplicateIdException if the loan exists
	public void insert(BookLoansId loanId) throws 
		EntityDoesNotExistException, DuplicateIdException, SQLException {

		BookLoans loan = generateLoan(loanId);
		saveLoan(loan);
	}
	
	
	//Also increments the noOfCopies 
	//Throws EntityDoesNotExistException if the loan exists
	public void delete(BookLoansId loanId) 
			throws EntityDoesNotExistException, SQLException {
		

		Optional<BookLoans> existing = loanRepo.findById(loanId);
		
		//throw if the loan does not exist
		if(existing.isEmpty()) {
			throw new EntityDoesNotExistException("loan");
		}
		
		
		
		BookCopiesId copiesId = new BookCopiesId();
		
		copiesId.setBookId(loanId.getBookId());
		copiesId.setBranchId(loanId.getBranchId());
		
		BookCopies copies = copiesRepo.findById(copiesId).get();
		
		//increment the noOfCopies
		copies.setNoOfCopies(copies.getNoOfCopies() + 1);
		copiesRepo.save(copies);
		
		
		
		loanRepo.deleteById(loanId);
	}
	
	
	//Validates the loan request
	//Does not perform any updates
	//Generates an outDate and dueDate
	private BookLoans generateLoan(BookLoansId loanId) 
			throws EntityDoesNotExistException, DuplicateIdException{
		
		//Validate the loan
		
		Optional<Borrower> optBorrower = borrowerRepo.findByCardNo(loanId.getCardNo());
		
		if(optBorrower.isEmpty()) {
			throw new EntityDoesNotExistException("borrower");
		}
		
		Borrower borrower = optBorrower.get();
		Optional<LibraryBranch> optBranch = branchRepo.findById(loanId.getBranchId());
		
		if(optBranch.isEmpty()) {
			throw new EntityDoesNotExistException("branch");
		}
		
		LibraryBranch branch = optBranch.get();
		
		Optional<Book> optBook = copiesRepo.getAvailableCopies(loanId.getBranchId())
				.stream()
				.map(BookCopies::getBook)
				.findAny();
				
		
		if(optBook.isEmpty()) {
			throw new EntityDoesNotExistException("book");
		}
		
		Book book = optBook.get();
		
		Optional<BookLoans> existing = loanRepo.findById(loanId);
		
		//Throw if the loan exists
		if(existing.isPresent()) {
			throw new DuplicateIdException();
		}
		
		
		
		//Create the loan
		
		LocalDate outDate = LocalDate.now();
		LocalDate dueDate = outDate.plusWeeks(1);
		
		BookLoans loan = new BookLoans(
				loanId, book, branch, borrower, 
				Date.valueOf(outDate),
				Date.valueOf(dueDate)
				);
		
		return loan;
	}
	
	private void saveLoan(BookLoans loan) {
		
		BookLoansId loanId = loan.getId();
		BookCopiesId copiesId = new BookCopiesId();
		
		copiesId.setBookId(loanId.getBookId());
		copiesId.setBranchId(loanId.getBranchId());
		
		BookCopies copies = copiesRepo.findById(copiesId).get();
		
		//decrement the noOfCopies
		copies.setNoOfCopies(copies.getNoOfCopies() - 1);
		copiesRepo.save(copies);
		

		
		loanRepo.save(loan);
	}
}
