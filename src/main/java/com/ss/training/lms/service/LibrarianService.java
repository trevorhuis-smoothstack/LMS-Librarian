package com.ss.training.lms.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.lms.dao.BookCopiesDAO;
import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.LibraryBranchDAO;
import com.ss.training.lms.entity.BookCopies;
import com.ss.training.lms.entity.LibraryBranch;

@Component
public class LibrarianService {
	
	@Autowired
	LibraryBranchDAO libDAO;
	
	@Autowired
	BookCopiesDAO entriesDAO;

	@Autowired
	BookDAO bookDAO;

	/**
	 * 
	 * @param entry
	 */
    public void updateCopies(BookCopies entry) {
        entriesDAO.save(entry);
    }

    /**
     * 
     * @param branch
     */
    public void updateBranch(LibraryBranch branch) {
        libDAO.save(branch);
    }

    /**
     * See my note in the controller class. - Trevor
    */

    // public List<LibraryBranch> getBranches() {
    //     return libDAO.findAll();
    // }

    // public BookCopies getAnEntryOfBookCopies(Integer branchId, Integer bookId) {
    //     return entriesDAO.findByBranchIdAndBookId(branchId, bookId);
    // }

    // public List<Book> getBooksWithSearch(String search) throws SQLException {
    //     Connection conn = null;
    //     try {
    //         return bookDAO.readAllBooksWithSearch(search, conn);
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return null;
    //     } finally {
	// 		if(conn!=null){
	// 			conn.close();
	// 		}
	// 	}
    // }

    // public List<Book> getBooksAtABranch(int branch) throws SQLException {
    //     List<Book> books = new ArrayList<>();
    //     List<BookCopies> entries = entriesDAO.readBooksFromABranch(branch);
    //     if(entries.size() == 0) {
    //         return null;
    //     }
    //     for(BookCopies entry: entries) {
    //         books.add(bookDAO.readABookById(entry.getBookId()).get(0));
    //     }
    //     return books;
    // }
}