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


    // @RequestMapping(path="/lms/librarian/books/{search}")
    // public ResponseEntity<List<Book>> getBooksWithSearch(@PathVariable String search)
    // {
    //     List<Book> books = null;;
    //     HttpStatus status = HttpStatus.OK;

    //     try {
    //         books = librarianService.getBooksWithSearch(search);
    //         if (books == null)
    //         {
    //             status = HttpStatus.NOT_FOUND;
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return new ResponseEntity<List<Book>>(books , HttpStatus.INTERNAL_SERVER_ERROR);

    //     }
    //     return new ResponseEntity<List<Book>>(books , status);

    // }

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

    @PutMapping(value = "/lms/librarian/branches/{branchId}/books/{bookId}")
    public ResponseEntity<BookCopies> updateCopies(@RequestBody BookCopies bookCopies)
        {
        HttpStatus status = HttpStatus.OK;
        if (bookCopies == null)
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<BookCopies>(bookCopies, status);
        }
        librarianService.updateCopies(bookCopies);
        return new ResponseEntity<BookCopies>(bookCopies, status);
    }

    @PutMapping(value = "/lms/librarian/branches")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LibraryBranch> updateBranch(@RequestBody LibraryBranch libraryBranch){
        HttpStatus status = HttpStatus.OK;
        if (libraryBranch == null)
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<LibraryBranch>(libraryBranch, status);
        }
        librarianService.updateBranch(libraryBranch);
        return new ResponseEntity<LibraryBranch>(libraryBranch, status);


    }
}