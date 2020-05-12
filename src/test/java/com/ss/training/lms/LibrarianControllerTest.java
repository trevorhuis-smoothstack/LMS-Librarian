package com.ss.training.lms;

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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.junit4.SpringRunner;

import com.ss.training.lms.controller.LibrarianController;
import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.service.LibrarianService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class LibrarianControllerTest {

	private static MockMvc mockMvc;
	
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
		System.out.println("Test get branches");
		// mock data to be returned. 
		List<LibraryBranch> branches = new ArrayList<LibraryBranch>();
		branches.add(new LibraryBranch(1, "name", "address"));
		
		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("branchName", "name");
		item.put("branchId", 1);
		item.put("branchAddress", "address");
		array.add(item);
		
		Mockito.when(librarianService.getBranches()).thenReturn(branches);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/librarian/branches"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(array.toString()));
	}
	
}
