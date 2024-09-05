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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.jher.nid_aux_histoires.NidAuxHistoiresApp;
import com.jher.nid_aux_histoires.domain.BookStatus;
import com.jher.nid_aux_histoires.repository.BookRepository;
import com.jher.nid_aux_histoires.repository.BookStatusRepository;
import com.jher.nid_aux_histoires.repository.ChapterRepository;
import com.jher.nid_aux_histoires.repository.UserRepository;
import com.jher.nid_aux_histoires.security.AuthoritiesConstants;
import com.jher.nid_aux_histoires.service.dto.BookStatusDTO;
import com.jher.nid_aux_histoires.service.mapper.BookStatusMapper;

/**
 * Integration tests for the {@link BookStatusResource} REST controller.
 */
@SpringBootTest(classes = NidAuxHistoiresApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BookStatusResourceIT {

	private static final Boolean DEFAULT_FINISHED = false;
	private static final Boolean UPDATED_FINISHED = true;

	private static final Boolean DEFAULT_FAVORIT = false;
	private static final Boolean UPDATED_FAVORIT = true;

	@Autowired
	private BookStatusRepository bookStatusRepository;

	@Autowired
	private BookStatusMapper bookStatusMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ChapterRepository chapterRepository;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restBookStatusMockMvc;

	private BookStatus bookStatus;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public BookStatus createEntity(EntityManager em) {
		BookStatus bookStatus = new BookStatus().finished(DEFAULT_FINISHED).favorit(DEFAULT_FAVORIT);

		bookStatus.setBook(bookRepository.getOne(1L));
		bookStatus.setUser(userRepository.getOne(3L));
		bookStatus.setCurentChapter(chapterRepository.getOne(1L));
		return bookStatus;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static BookStatus createUpdatedEntity(EntityManager em) {
		BookStatus bookStatus = new BookStatus().finished(UPDATED_FINISHED).favorit(UPDATED_FAVORIT);
		return bookStatus;
	}

	@BeforeEach
	public void initTest() {
		bookStatus = createEntity(em);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void createBookStatus() throws Exception {
		int databaseSizeBeforeCreate = bookStatusRepository.findAll().size();
		// Create the BookStatus
		BookStatusDTO bookStatusDTO = bookStatusMapper.toDto(bookStatus);
		restBookStatusMockMvc.perform(post("/api/bookStatuses").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(bookStatusDTO))).andExpect(status().isCreated());

		// Validate the BookStatus in the database
		List<BookStatus> bookStatusList = bookStatusRepository.findAll();
		assertThat(bookStatusList).hasSize(databaseSizeBeforeCreate + 1);
		BookStatus testBookStatus = bookStatusList.get(bookStatusList.size() - 1);
		assertThat(testBookStatus.isFinished()).isEqualTo(DEFAULT_FINISHED);
		assertThat(testBookStatus.isFavorit()).isEqualTo(DEFAULT_FAVORIT);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void createBookStatusWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = bookStatusRepository.findAll().size();

		// Create the BookStatus with an existing ID
		bookStatus.setId(1L);
		BookStatusDTO bookStatusDTO = bookStatusMapper.toDto(bookStatus);

		// An entity with an existing ID cannot be created, so this API call must fail
		restBookStatusMockMvc.perform(post("/api/bookStatuses").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(bookStatusDTO))).andExpect(status().isBadRequest());

		// Validate the BookStatus in the database
		List<BookStatus> bookStatusList = bookStatusRepository.findAll();
		assertThat(bookStatusList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void getAllBookStatuses() throws Exception {
		// Initialize the database
		bookStatusRepository.saveAndFlush(bookStatus);

		// Get all the bookStatusList
		restBookStatusMockMvc.perform(get("/api/bookStatuses?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(bookStatus.getId().intValue())))
				.andExpect(jsonPath("$.[*].finished").value(hasItem(DEFAULT_FINISHED.booleanValue())))
				.andExpect(jsonPath("$.[*].favorit").value(hasItem(DEFAULT_FAVORIT.booleanValue())));
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void getBookStatus() throws Exception {
		// Initialize the database
		bookStatusRepository.saveAndFlush(bookStatus);

		// Get the bookStatus
		restBookStatusMockMvc.perform(get("/api/bookStatuses/{id}", bookStatus.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(bookStatus.getId().intValue()))
				.andExpect(jsonPath("$.finished").value(DEFAULT_FINISHED.booleanValue()))
				.andExpect(jsonPath("$.favorit").value(DEFAULT_FAVORIT.booleanValue()));
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void getNonExistingBookStatus() throws Exception {
		// Get the bookStatus
		restBookStatusMockMvc.perform(get("/api/bookStatuses/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void updateBookStatus() throws Exception {
		// Initialize the database
		bookStatusRepository.saveAndFlush(bookStatus);

		int databaseSizeBeforeUpdate = bookStatusRepository.findAll().size();

		// Update the bookStatus
		BookStatus updatedBookStatus = bookStatusRepository.findById(bookStatus.getId()).get();
		// Disconnect from session so that the updates on updatedBookStatus are not
		// directly saved in db
		em.detach(updatedBookStatus);
		updatedBookStatus.finished(UPDATED_FINISHED).favorit(UPDATED_FAVORIT);
		BookStatusDTO bookStatusDTO = bookStatusMapper.toDto(updatedBookStatus);

		restBookStatusMockMvc.perform(put("/api/bookStatuses").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(bookStatusDTO))).andExpect(status().isOk());

		// Validate the BookStatus in the database
		List<BookStatus> bookStatusList = bookStatusRepository.findAll();
		assertThat(bookStatusList).hasSize(databaseSizeBeforeUpdate);
		BookStatus testBookStatus = bookStatusList.get(bookStatusList.size() - 1);
		assertThat(testBookStatus.isFinished()).isEqualTo(UPDATED_FINISHED);
		assertThat(testBookStatus.isFavorit()).isEqualTo(UPDATED_FAVORIT);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void updateNonExistingBookStatus() throws Exception {
		int databaseSizeBeforeUpdate = bookStatusRepository.findAll().size();

		// Create the BookStatus
		BookStatusDTO bookStatusDTO = bookStatusMapper.toDto(bookStatus);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restBookStatusMockMvc.perform(put("/api/bookStatuses").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(bookStatusDTO))).andExpect(status().isBadRequest());

		// Validate the BookStatus in the database
		List<BookStatus> bookStatusList = bookStatusRepository.findAll();
		assertThat(bookStatusList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void deleteBookStatus() throws Exception {
		// Initialize the database
		bookStatusRepository.saveAndFlush(bookStatus);

		int databaseSizeBeforeDelete = bookStatusRepository.findAll().size();

		// Delete the bookStatus
		restBookStatusMockMvc
				.perform(delete("/api/bookStatuses/{id}", bookStatus.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<BookStatus> bookStatusList = bookStatusRepository.findAll();
		assertThat(bookStatusList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
