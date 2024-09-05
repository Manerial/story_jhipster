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
import com.jher.nid_aux_histoires.domain.Chapter;
import com.jher.nid_aux_histoires.repository.ChapterRepository;
import com.jher.nid_aux_histoires.repository.PartRepository;
import com.jher.nid_aux_histoires.security.AuthoritiesConstants;
import com.jher.nid_aux_histoires.service.ChapterService;
import com.jher.nid_aux_histoires.service.dto.ChapterDTO;
import com.jher.nid_aux_histoires.service.mapper.ChapterMapper;

/**
 * Integration tests for the {@link ChapterResource} REST controller.
 */
@SpringBootTest(classes = NidAuxHistoiresApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChapterResourceIT {

	private static final String DEFAULT_NAME = "AAAAAAAAAA";
	private static final String UPDATED_NAME = "BBBBBBBBBB";

	private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
	private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

	private static final Integer DEFAULT_NUMBER = 1;
	private static final Integer UPDATED_NUMBER = 2;

	@Autowired
	private ChapterRepository chapterRepository;

	@Mock
	private ChapterRepository chapterRepositoryMock;

	@Autowired
	private PartRepository partRepository;

	@Autowired
	private ChapterMapper chapterMapper;

	@Mock
	private ChapterService chapterServiceMock;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restChapterMockMvc;

	private Chapter chapter;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public Chapter createEntity(EntityManager em) {
		Chapter chapter = new Chapter().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION).number(DEFAULT_NUMBER);
		chapter.setPart(partRepository.getOne(1L));
		return chapter;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public Chapter createUpdatedEntity(EntityManager em) {
		Chapter chapter = new Chapter().name(UPDATED_NAME).description(UPDATED_DESCRIPTION).number(UPDATED_NUMBER);
		chapter.setPart(partRepository.getOne(1L));
		return chapter;
	}

	@BeforeEach
	public void initTest() {
		chapter = createEntity(em);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void createChapter() throws Exception {
		int databaseSizeBeforeCreate = chapterRepository.findAll().size();
		// Create the Chapter
		ChapterDTO chapterDTO = chapterMapper.toDto(chapter);
		restChapterMockMvc.perform(post("/api/chapters").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(chapterDTO))).andExpect(status().isCreated());

		// Validate the Chapter in the database
		List<Chapter> chapterList = chapterRepository.findAll();
		assertThat(chapterList).hasSize(databaseSizeBeforeCreate + 1);
		Chapter testChapter = chapterList.get(chapterList.size() - 1);
		assertThat(testChapter.getName()).isEqualTo(DEFAULT_NAME);
		assertThat(testChapter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testChapter.getNumber()).isEqualTo(DEFAULT_NUMBER);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void createChapterWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = chapterRepository.findAll().size();

		// Create the Chapter with an existing ID
		chapter.setId(1L);
		ChapterDTO chapterDTO = chapterMapper.toDto(chapter);

		// An entity with an existing ID cannot be created, so this API call must fail
		restChapterMockMvc.perform(post("/api/chapters").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(chapterDTO))).andExpect(status().isBadRequest());

		// Validate the Chapter in the database
		List<Chapter> chapterList = chapterRepository.findAll();
		assertThat(chapterList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void getAllChapters() throws Exception {
		// Initialize the database
		chapterRepository.saveAndFlush(chapter);

		// Get all the chapterList
		restChapterMockMvc.perform(get("/api/chapters?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(chapter.getId().intValue())))
				.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
				.andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
				.andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void getChapter() throws Exception {
		// Initialize the database
		chapterRepository.saveAndFlush(chapter);

		// Get the chapter
		restChapterMockMvc.perform(get("/api/chapters/{id}", chapter.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(chapter.getId().intValue()))
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME))
				.andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
				.andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void getNonExistingChapter() throws Exception {
		// Get the chapter
		restChapterMockMvc.perform(get("/api/chapters/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void updateChapter() throws Exception {
		// Initialize the database
		chapterRepository.saveAndFlush(chapter);

		int databaseSizeBeforeUpdate = chapterRepository.findAll().size();

		// Update the chapter
		Chapter updatedChapter = chapterRepository.findById(chapter.getId()).get();
		// Disconnect from session so that the updates on updatedChapter are not
		// directly saved in db
		em.detach(updatedChapter);
		updatedChapter.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).number(UPDATED_NUMBER);
		ChapterDTO chapterDTO = chapterMapper.toDto(updatedChapter);

		restChapterMockMvc.perform(put("/api/chapters").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(chapterDTO))).andExpect(status().isOk());

		// Validate the Chapter in the database
		List<Chapter> chapterList = chapterRepository.findAll();
		assertThat(chapterList).hasSize(databaseSizeBeforeUpdate);
		Chapter testChapter = chapterList.get(chapterList.size() - 1);
		assertThat(testChapter.getName()).isEqualTo(UPDATED_NAME);
		assertThat(testChapter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
		assertThat(testChapter.getNumber()).isEqualTo(UPDATED_NUMBER);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void updateNonExistingChapter() throws Exception {
		int databaseSizeBeforeUpdate = chapterRepository.findAll().size();

		// Create the Chapter
		ChapterDTO chapterDTO = chapterMapper.toDto(chapter);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restChapterMockMvc.perform(put("/api/chapters").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(chapterDTO))).andExpect(status().isBadRequest());

		// Validate the Chapter in the database
		List<Chapter> chapterList = chapterRepository.findAll();
		assertThat(chapterList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void deleteChapter() throws Exception {
		// Initialize the database
		chapterRepository.saveAndFlush(chapter);

		int databaseSizeBeforeDelete = chapterRepository.findAll().size();

		// Delete the chapter
		restChapterMockMvc.perform(delete("/api/chapters/{id}", chapter.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Chapter> chapterList = chapterRepository.findAll();
		assertThat(chapterList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
