package com.jher.nid_aux_histoires.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.NidAuxHistoiresApp;
import com.jher.nid_aux_histoires.domain.Book;
import com.jher.nid_aux_histoires.repository.BookRepository;
import com.jher.nid_aux_histoires.service.BookService;
import com.jher.nid_aux_histoires.service.dto.BookDTO;
import com.jher.nid_aux_histoires.service.mapper.BookMapper;

/**
 * Integration tests for the {@link BookResource} REST controller.
 */
@SpringBootTest(classes = NidAuxHistoiresApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BookResourceIT {

	private static final String DEFAULT_NAME = "AAAAAAAAAA";
	private static final String UPDATED_NAME = "BBBBBBBBBB";

	@Autowired
	private BookRepository bookRepository;

	@Mock
	private BookRepository bookRepositoryMock;

	@Autowired
	private BookMapper bookMapper;

	@Mock
	private BookService bookServiceMock;

	@Autowired
	private BookService bookService;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restBookMockMvc;

	private Book book;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Book createEntity(EntityManager em) {
		Book book = new Book().name(DEFAULT_NAME);
		return book;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Book createUpdatedEntity(EntityManager em) {
		Book book = new Book().name(UPDATED_NAME);
		return book;
	}

	@BeforeEach
	public void initTest() {
		book = createEntity(em);
	}

	@Test
	@Transactional
	public void createBook() throws Exception {
		int databaseSizeBeforeCreate = bookRepository.findAll().size();
		// Create the Book
		BookDTO bookDTO = bookMapper.toDto(book);
		restBookMockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(bookDTO))).andExpect(status().isCreated());

		// Validate the Book in the database
		List<Book> bookList = bookRepository.findAll();
		assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
		Book testBook = bookList.get(bookList.size() - 1);
		assertThat(testBook.getName()).isEqualTo(DEFAULT_NAME);
	}

	@Test
	@Transactional
	public void createBookWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = bookRepository.findAll().size();

		// Create the Book with an existing ID
		book.setId(1L);
		BookDTO bookDTO = bookMapper.toDto(book);

		// An entity with an existing ID cannot be created, so this API call must fail
		restBookMockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(bookDTO))).andExpect(status().isBadRequest());

		// Validate the Book in the database
		List<Book> bookList = bookRepository.findAll();
		assertThat(bookList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllBooks() throws Exception {
		// Initialize the database
		bookRepository.saveAndFlush(book);

		// Get all the bookList
		restBookMockMvc.perform(get("/api/books?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
				.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
	}

	@SuppressWarnings({ "unchecked" })
	public void getAllBooksWithEagerRelationshipsIsEnabled() throws Exception {
		when(bookServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

		restBookMockMvc.perform(get("/api/books?eagerload=true")).andExpect(status().isOk());

		verify(bookServiceMock, times(1)).findAllWithEagerRelationships(any());
	}

	@SuppressWarnings({ "unchecked" })
	public void getAllBooksWithEagerRelationshipsIsNotEnabled() throws Exception {
		when(bookServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

		restBookMockMvc.perform(get("/api/books?eagerload=true")).andExpect(status().isOk());

		verify(bookServiceMock, times(1)).findAllWithEagerRelationships(any());
	}

	@Test
	@Transactional
	public void getBook() throws Exception {
		// Initialize the database
		bookRepository.saveAndFlush(book);

		// Get the book
		restBookMockMvc.perform(get("/api/books/{id}", book.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(book.getId().intValue()))
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME));
	}

	@Test
	@Transactional
	public void getNonExistingBook() throws Exception {
		// Get the book
		restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateBook() throws Exception {
		// Initialize the database
		bookRepository.saveAndFlush(book);

		int databaseSizeBeforeUpdate = bookRepository.findAll().size();

		// Update the book
		Book updatedBook = bookRepository.findById(book.getId()).get();
		// Disconnect from session so that the updates on updatedBook are not directly
		// saved in db
		em.detach(updatedBook);
		updatedBook.name(UPDATED_NAME);
		BookDTO bookDTO = bookMapper.toDto(updatedBook);

		restBookMockMvc.perform(put("/api/books").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(bookDTO))).andExpect(status().isOk());

		// Validate the Book in the database
		List<Book> bookList = bookRepository.findAll();
		assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
		Book testBook = bookList.get(bookList.size() - 1);
		assertThat(testBook.getName()).isEqualTo(UPDATED_NAME);
	}

	@Test
	@Transactional
	public void updateNonExistingBook() throws Exception {
		int databaseSizeBeforeUpdate = bookRepository.findAll().size();

		// Create the Book
		BookDTO bookDTO = bookMapper.toDto(book);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restBookMockMvc.perform(put("/api/books").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(bookDTO))).andExpect(status().isBadRequest());

		// Validate the Book in the database
		List<Book> bookList = bookRepository.findAll();
		assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteBook() throws Exception {
		// Initialize the database
		bookRepository.saveAndFlush(book);

		int databaseSizeBeforeDelete = bookRepository.findAll().size();

		// Delete the book
		restBookMockMvc.perform(delete("/api/books/{id}", book.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Book> bookList = bookRepository.findAll();
		assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
