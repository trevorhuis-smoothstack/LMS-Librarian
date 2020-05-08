package com.ss.training.lms.controller;

import java.sql.SQLException;
import java.util.List;

import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.BookCopies;
import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.service.LibrarianService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibrarianController {

    @Autowired
    LibrarianService librarianService;

    @GetMapping(path="/lms/librarian/branches",
                produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
                })
    public ResponseEntity<List<LibraryBranch>> getBranches()
     {
        List<LibraryBranch> branches = null;
        try {
			branches = librarianService.getBranches();
		} catch (final SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<List<LibraryBranch>>(branches , HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity<List<LibraryBranch>>(branches , HttpStatus.OK);
     }


    @RequestMapping(path="/lms/librarian/books/{search}",
                    produces = {
                        MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE
                    })
    public ResponseEntity<List<Book>> getBooksWithSearch(@PathVariable String search)
    {
        List<Book> books = null;

        try {
            books = librarianService.getBooksWithSearch(search);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<List<Book>>(books , HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<List<Book>>(books , HttpStatus.OK);

    }

    @RequestMapping(path="lms/librarian/branches/{branch}/books/{book}/copies",
                    produces = {
                        MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE
                    })
    public ResponseEntity<BookCopies> getAnEntryOfBookCopies(@PathVariable int branch, @PathVariable int book)
    {
        BookCopies bookCopies = null;

        try {
            bookCopies = librarianService.getAnEntryOfBookCopies(branch, book);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<BookCopies>(bookCopies , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<BookCopies>(bookCopies , HttpStatus.OK);

    }

    @GetMapping(path="lms/librarian/branches/{branchId}/books")
    public ResponseEntity<List<Book>>getBooksAtABranch(@PathVariable int branchId)
    {
        List<Book> books = null;

        try {
            books = librarianService.getBooksAtABranch(branchId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<List<Book>>(books, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);

    }
    
    // @PostMapping
    // public ResponseEntity<UUID> createVehicle(@RequestBody VehicleCreateDTO vehicleCreateDTO){
    //     return new ResponseEntity<>(vehicleCommandService.createVehicle(vehicleCreateDTO), HttpStatus.CREATED);
    // }

    @PutMapping(value = "/lms/librarian/branches/{branchId}/books/{bookId}")
    public void updateCopies(@PathVariable int branchId,
                             @PathVariable int bookId,
                             @RequestBody BookCopies bookCopies){
        try {
            librarianService.updateCopies(bookCopies);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PutMapping(value = "/lms/librarian/branches/{branchId}/update")
    public void updateBranch(@PathVariable int branchId,
                             @RequestBody LibraryBranch libraryBranch){
        try {
            librarianService.updateBranch(libraryBranch);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}