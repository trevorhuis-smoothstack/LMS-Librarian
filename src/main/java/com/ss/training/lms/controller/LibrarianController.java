package com.ss.training.lms.controller;
import com.ss.training.lms.entity.BookCopies;
import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.service.LibrarianService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibrarianController {

    @Autowired
    LibrarianService librarianService;

    /** 
     * Hey Sean, I see you added these functions and I missed this during the code review. These are not any API functionality we need.
     * I was using them to make the terminal application more user friendly. I will leave it in for you to decide what happens.
     * The only two stated functions of the Librarian is update the number of copies for a book and update details of a branch. - Trevor
    */

    // @GetMapping(path="/lms/librarian/branches",
    //             produces = {
    //                 MediaType.APPLICATION_XML_VALUE,
    //                 MediaType.APPLICATION_JSON_VALUE
    //             })
    // public ResponseEntity<List<LibraryBranch>> getBranches()
    //  {
    //     HttpStatus status = HttpStatus.OK;
    //     List<LibraryBranch> branches = librarianService.getBranches();
    //     if (branches == null){
    //         status = HttpStatus.NOT_FOUND;
    //     }
    //     return new ResponseEntity<List<LibraryBranch>>(branches , status);
    // }

    // @RequestMapping(path="/lms/librarian/books/{search}",
    //                 produces = {
    //                     MediaType.APPLICATION_XML_VALUE,
    //                     MediaType.APPLICATION_JSON_VALUE
    //                 })
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

    // @RequestMapping(path="lms/librarian/branches/{branch}/books/{book}/copies",
    //                 produces = {
    //                     MediaType.APPLICATION_XML_VALUE,
    //                     MediaType.APPLICATION_JSON_VALUE
    //                 })
    // public ResponseEntity<BookCopies> getAnEntryOfBookCopies(@PathVariable int branch, @PathVariable int book)
    // {
    //     HttpStatus status = HttpStatus.OK;
    //     BookCopies bookCopies = librarianService.getAnEntryOfBookCopies(branch, book);
    //     if (bookCopies == null)
    //     {
    //         status = HttpStatus.NOT_FOUND;
    //     }
    //     return new ResponseEntity<BookCopies>(bookCopies , status);

    // }

    // @GetMapping(path="lms/librarian/branches/{branchId}/books")
    // public ResponseEntity<List<Book>>getBooksAtABranch(@PathVariable int branchId)
    // {
    //     List<Book> books = null;
    //     HttpStatus status = HttpStatus.OK;

    //     try {
    //         books = librarianService.getBooksAtABranch(branchId);
    //         if (books == null)
    //         {
    //             status = HttpStatus.NOT_FOUND;
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return new ResponseEntity<List<Book>>(books, HttpStatus.INTERNAL_SERVER_ERROR);

    //     }
    //     return new ResponseEntity<List<Book>>(books, status);

    // }

    @PutMapping(value = "/lms/librarian/branches/{branchId}/books/{bookId}")
    public ResponseEntity<String> updateCopies(@PathVariable int branchId,
                                               @PathVariable int bookId,
                                               @RequestBody BookCopies bookCopies)
        {
        HttpStatus status = HttpStatus.OK;
        if (bookCopies == null)
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<String>("Problem with request", status);
        }
        librarianService.updateCopies(bookCopies);
        return new ResponseEntity<String>("success", status);
    }

    @PutMapping(value = "/lms/librarian/branches/{branchId}/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateBranch(@PathVariable int branchId,
                             @RequestBody LibraryBranch libraryBranch){
        HttpStatus status = HttpStatus.OK;
        if (libraryBranch == null)
        {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<String>("Problem with request", status);
        }
        librarianService.updateBranch(libraryBranch);
        return new ResponseEntity<String>("success", status);

    }
}