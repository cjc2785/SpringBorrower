package com.ss.lms.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;



@Component
public class TestUtils {
	
	
    @PersistenceContext
    private EntityManager em;

    


	//Deletes all rows in the db before populating
	public void populateTestDb()  {

		String authorsSql = "INSERT INTO library.tbl_author "
				+ "VALUES (1, 'joe')," 
				+ "(2, 'jake')," 
				+ "(3, 'john')";

		String publishersSQL = "INSERT INTO library.tbl_publisher "
				+ "VALUES (1, 'perry inc', '1 perry street', 111-111-1111),"
				+ "(2, 'paul inc', '1 paul street', 222-222-2222),"
				+ "(3, 'pat inc', '1 pat street', '333-333-3333')";
		
		String branchesSql = "INSERT INTO library.tbl_library_branch " 
				+ "VALUES (1, 'chi branch', '1 chi street'),"
				+ "(2, 'la branch', '1 la street'),"
				+ "(3, 'dc branch', '1 dc ave')," 
				+ "(4, 'miami branch', '1 miami ave'),"
				+ "(5, 'nyc branch', '1 nyc ave')";

		String borrowersSql = "INSERT INTO library.tbl_borrower "
				+ "VALUES (1, 'don', '1 amy street', '111-111-1111')," 
				+ "(2, 'dan', '1 dan street', '222-222-2222'),"
				+ "(3, 'dav', '1 tom street', '222-222-2222')";


		String booksSql = "INSERT INTO library.tbl_book " 
				+ "VALUES (1, 'baseball', 2, 1),"
				+ "(2, 'canada', 1, 2),"
				+ "(3, 'africa', 1, 2),"
				+ "(4, 'football', 2, 1),"
				+ "(5, 'spain', 1, 2)";

		String copiesSql = "INSERT INTO library.tbl_book_copies "
				+ "VALUES (5, 1, 1), "
				+ "(5, 2, 1),"
				+ "(1, 3, 1),"
				+ "(2, 3, 0),"
				+ "(3, 3, 3),"
				+ "(4, 3, 3),"
				+ "(1, 4, 1),"
				+ "(3, 4, 3)"; 

		String loansSql = "INSERT INTO library.tbl_book_loans " 
				+ "VALUES (5, 1, 2, CURDATE(), CURDATE() + INTERVAL 1 WEEK),"
				+ "(5, 2, 2, CURDATE(), CURDATE() + INTERVAL 1 WEEK),"
				+ "(1, 3, 2, CURDATE(), CURDATE() + INTERVAL 1 WEEK),"
				+ "(2, 3, 2, CURDATE(), CURDATE() + INTERVAL 1 WEEK),"
				+ "(3, 3, 2, CURDATE(), CURDATE() + INTERVAL 1 WEEK),"
				+ "(3, 3, 3, CURDATE(), CURDATE() + INTERVAL 1 WEEK)";

	
		//Delete all rows in the database 
	
		em.createNativeQuery("DELETE FROM library.tbl_author").executeUpdate();
		em.createNativeQuery("DELETE FROM library.tbl_publisher").executeUpdate();
		em.createNativeQuery("DELETE FROM library.tbl_library_branch").executeUpdate();
		em.createNativeQuery("DELETE FROM library.tbl_borrower").executeUpdate();
		
		//Populate the database
		em.createNativeQuery(authorsSql).executeUpdate();
		em.createNativeQuery(publishersSQL).executeUpdate();
		em.createNativeQuery(branchesSql).executeUpdate();
		em.createNativeQuery(borrowersSql).executeUpdate();
		em.createNativeQuery(booksSql).executeUpdate();
		em.createNativeQuery(copiesSql).executeUpdate();
		em.createNativeQuery(loansSql).executeUpdate();
	}
}
