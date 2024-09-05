package com.jher.nid_aux_histoires.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.NidAuxHistoiresApp;
import com.jher.nid_aux_histoires.domain.Book;
import com.jher.nid_aux_histoires.repository.BookRepository;
import com.jher.nid_aux_histoires.repository.CoverRepository;
import com.jher.nid_aux_histoires.repository.UserRepository;
import com.jher.nid_aux_histoires.security.AuthoritiesConstants;
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
	private UserRepository userRepository;

	@Autowired
	private CoverRepository coverRepository;

	@Autowired
	private BookMapper bookMapper;

	@Mock
	private BookService bookServiceMock;

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
	public Book createEntity(EntityManager em) {
		Book book = new Book().name(DEFAULT_NAME);
		book.setVisibility(true);
		book.setAuthor(userRepository.getOne(1L));
		book.setCover(coverRepository.getOne(1L));
		return book;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public Book createUpdatedEntity(EntityManager em) {
		Book book = new Book().name(UPDATED_NAME);
		book.setVisibility(false);
		book.setAuthor(userRepository.getOne(1L));
		return book;
	}

	@BeforeEach
	public void initTest() {
		book = createEntity(em);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
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
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
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

	@Test
	@Transactional
	public void getAllFavorits() throws Exception {
		// Initialize the database
		bookRepository.saveAndFlush(book);

		// Get all the bookList
		restBookMockMvc.perform(get("/api/books/favorits")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	@Transactional
	public void getBooksByAuthor() throws Exception {
		// Initialize the database
		bookRepository.saveAndFlush(book);

		// Get all the bookList
		restBookMockMvc.perform(get("/api/books/author/{login}", book.getAuthor().getLogin()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
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
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void getNonExistingBook() throws Exception {
		// Get the book
		restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE)).andExpect(status().is4xxClientError());
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
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
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void updateBookVisibility() throws Exception {
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

		restBookMockMvc.perform(put("/api/books/visibility/{id}", book.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(bookDTO))).andExpect(status().isOk());

		// Validate the Book in the database
		List<Book> bookList = bookRepository.findAll();
		assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
		Book testBook = bookList.get(bookList.size() - 1);
		assertThat(testBook.getVisibility()).isEqualTo(false);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
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
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
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
