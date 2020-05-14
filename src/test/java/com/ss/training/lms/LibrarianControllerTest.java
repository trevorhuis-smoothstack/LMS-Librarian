package com.ss.training.lms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.junit4.SpringRunner;

import com.ss.training.lms.controller.LibrarianController;
import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.BookCopies;
import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.service.LibrarianService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class LibrarianControllerTest {

	private MockMvc mockMvc;
	
	@Mock
    LibrarianService librarianService;
	
	@InjectMocks
	private LibrarianController librarianController;
	
	@BeforeAll
	public void setup() throws Exception
	{
		mockMvc = MockMvcBuilders.standaloneSetup(librarianController).build();
	}
	
	@Test
	public void testGetBranches() throws Exception
	{
		// mock data to be returned. 
		List<LibraryBranch> branches = new ArrayList<LibraryBranch>();
		branches.add(new LibraryBranch(1, "name", "address"));
		
		// create json to test the response from the controller.
		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("branchName", "name");
		item.put("branchId", 1);
		item.put("branchAddress", "address");
		array.add(item);
		
		// Tell librarian service to return the branches I created earlier when called.
		Mockito.when(librarianService.getBranches()).thenReturn(branches);
		
		// make a request to the controller through the mock and check the results
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/librarian/branches").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(array.toString()));
		
		// Tell librarian service to return null when get branches is called.
		Mockito.when(librarianService.getBranches()).thenReturn(null);
		
		// test the return when the database is empty
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/librarian/branches"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}
	
	@Test
	public void testGetBooksWithSearch() throws Exception
	{
		List<Book> books = new ArrayList<Book>();
		books.add(new Book(1,"title1", 1));
		books.add(new Book(2,"title2", 2));
		
		Mockito.when(librarianService.getBooksWithSearch("title")).thenReturn(books);
		
		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("bookId", 1);
		item.put("title", "title1");
		item.put("publisherId", 1);
		array.add(item);
		
		JSONObject item2 = new JSONObject();
		item2.put("bookId", 2);
		item2.put("title", "title2");
		item2.put("publisherId", 2);
		array.add(item2);
		
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/librarian/books/title").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(array.toString()));
		
		// test empty return
		Mockito.when(librarianService.getBooksWithSearch("title")).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/librarian/books/title"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testGetAnEntryOfBookCopies() throws Exception
	{
		BookCopies bookCopies = new BookCopies(1,2,3);
		Mockito.when(librarianService.getAnEntryOfBookCopies(2, 1)).thenReturn(bookCopies);
		
		JSONObject item = new JSONObject();
		item.put("bookId", 1);
		item.put("branchId", 2);
		item.put("noOfCopies", 3);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/librarian/branches/2/books/1/copies").accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(item.toString()));
		
		// test empty return
		Mockito.when(librarianService.getAnEntryOfBookCopies(2,1)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/librarian/branches/2/books/1/copies"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}
	
	@Test
	public void testGetBooksAtABranch() throws Exception
	{
		List<Book> booksAtBranch = new ArrayList<Book>();
		booksAtBranch.add(new Book(1, "title", 1));
		Mockito.when(librarianService.getBooksAtABranch(1)).thenReturn(booksAtBranch);
		
		JSONArray books = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("bookId", 1);
		item.put("title", "title");
		item.put("publisherId", 1);
		books.add(item);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/librarian/branches/1/books").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(books.toString()));
		
		Mockito.when(librarianService.getBooksAtABranch(1)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/librarian/branches/1/books"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testUpdateCopies() throws Exception
	{
		
		BookCopies bookCopies = new BookCopies(1,1,1);
		
		Mockito.when(librarianService.updateCopies(bookCopies)).thenReturn(true);
		JSONObject item = new JSONObject();
		item.put("bookId", 1);
		item.put("branchId", 1);
		item.put("noOfCopies", 1);
		   
        mockMvc.perform(MockMvcRequestBuilders.put("/lms/librarian/branches/1/copies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(item.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(item.toString()));
        
		Mockito.when(librarianService.updateCopies(bookCopies)).thenReturn(false);
		   
        mockMvc.perform(MockMvcRequestBuilders.put("/lms/librarian/branches/1/copies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(item.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        
	}
	
	@Test
	public void testUpdateBranch() throws Exception
	{
		LibraryBranch branch = new LibraryBranch(1,"name", "address");
		Mockito.when(librarianService.updateBranch(branch)).thenReturn(true);
		
		JSONObject item = new JSONObject();
		item.put("branchId", 1);
		item.put("branchName", "name");
		item.put("branchAddress", "address");

	    mockMvc.perform(MockMvcRequestBuilders.put("/lms/librarian/branches/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(item.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(item.toString()));
	    
		Mockito.when(librarianService.updateBranch(branch)).thenReturn(false);
		 mockMvc.perform(MockMvcRequestBuilders.put("/lms/librarian/branches/1")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(item.toString()))
	                .andExpect(MockMvcResultMatchers.status().isBadRequest());
	    
	}
}
