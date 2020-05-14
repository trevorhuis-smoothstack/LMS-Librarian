package com.ss.training.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.dao.BookCopiesDAO;
import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.LibraryBranchDAO;
import com.ss.training.lms.entity.Book;
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
     * @return
     */ 
    public boolean updateCopies(BookCopies entry) {
        BookCopies entryToUpdate = entriesDAO.findByBranchIdAndBookId(entry.getBranchId(), entry.getBookId());
        if(entryToUpdate == null)
            return false;
        entriesDAO.save(entry);
        return true;
    }

    /**
     * 
     * @param branch
     * @return
     */
    public boolean updateBranch(LibraryBranch branch) {
        LibraryBranch branchToUpdate = libDAO.findByBranchId(branch.getBranchId());
        if(branchToUpdate == null)
            return false;
        libDAO.save(branch);
        return true;
    }

    /**
     * 
     * @return
     */
    public List<LibraryBranch> getBranches() {
        return libDAO.findAll();
    }

    /**
     * 
     * @param branchId
     * @param bookId
     * @return
     */
    public BookCopies getAnEntryOfBookCopies(Integer branchId, Integer bookId) {
        return entriesDAO.findByBranchIdAndBookId(branchId, bookId);
    }

    /**
     * 
     * @param branchId
     * @return
     */
    public List<Book> getBooksAtABranch(int branchId) {
        List<Book> books = new ArrayList<>();
        List<BookCopies> entries = entriesDAO.findByBranchId(branchId);
        if(entries.size() == 0) {
            return null;
        }
        for(BookCopies entry: entries) {
            books.add(bookDAO.findByBookId(entry.getBookId()));
        }
        return books;
    }
}