package com.ss.training.lms.controller;

import java.util.List;

import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.BookCopies;
import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.service.LibrarianService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibrarianController {

    @Autowired
    LibrarianService librarianService;

    @GetMapping(path="/lms/librarian/branches")
    public ResponseEntity<List<LibraryBranch>> getBranches()
     {
        HttpStatus status = HttpStatus.OK;
        List<LibraryBranch> branches = null;

        branches = librarianService.getBranches();
        if (branches == null){
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<List<LibraryBranch>>(branches , status);
     }



    @RequestMapping(path="lms/librarian/branches/{branch}/books/{book}/copies")
    public ResponseEntity<BookCopies> getAnEntryOfBookCopies(@PathVariable int branch, @PathVariable int book)
    {
        BookCopies bookCopies = null;
        HttpStatus status = HttpStatus.OK;

            bookCopies = librarianService.getAnEntryOfBookCopies(branch, book);
            if (bookCopies == null)
            {
                status = HttpStatus.NOT_FOUND;
            }
        return new ResponseEntity<BookCopies>(bookCopies , status);

    }

    @GetMapping(path="lms/librarian/branches/{branchId}/books")
    public ResponseEntity<List<Book>>getBooksAtABranch(@PathVariable int branchId)
    {
        List<Book> books = null;
        HttpStatus status = HttpStatus.OK;

        books = librarianService.getBooksAtABranch(branchId);
        if (books == null)
        {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<List<Book>>(books, status);

    }


    @PutMapping(value = "/lms/librarian/branches/{branch}/copies")
    public ResponseEntity<BookCopies> updateCopies(@PathVariable int branch,
                                               @RequestBody BookCopies bookCopies)
        {
        HttpStatus status = HttpStatus.OK;
        if (bookCopies == null || bookCopies.getBookId() == null || bookCopies.getBranchId() == null)
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<BookCopies>(bookCopies, status);
        }
        
        boolean didUpdate = librarianService.updateCopies(bookCopies);
        if(!didUpdate)
            return new ResponseEntity<BookCopies>(bookCopies, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<BookCopies>(bookCopies, status);
    }

    @PutMapping(value = "/lms/librarian/branches/{branchId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LibraryBranch> updateBranch(@PathVariable int branchId,
                             @RequestBody LibraryBranch libraryBranch){
        HttpStatus status = HttpStatus.OK;
        if (libraryBranch == null)
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<LibraryBranch>(libraryBranch, status);
        }
        boolean didUpdate = librarianService.updateBranch(libraryBranch);
        if(!didUpdate)
            return new ResponseEntity<LibraryBranch>(libraryBranch, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<LibraryBranch>(libraryBranch, status);


    }
}