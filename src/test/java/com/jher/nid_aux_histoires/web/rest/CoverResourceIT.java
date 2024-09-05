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
import org.springframework.util.Base64Utils;

import com.jher.nid_aux_histoires.NidAuxHistoiresApp;
import com.jher.nid_aux_histoires.domain.Cover;
import com.jher.nid_aux_histoires.repository.CoverRepository;
import com.jher.nid_aux_histoires.security.AuthoritiesConstants;
import com.jher.nid_aux_histoires.service.CoverService;
import com.jher.nid_aux_histoires.service.dto.CoverDTO;
import com.jher.nid_aux_histoires.service.mapper.CoverMapper;

/**
 * Integration tests for the {@link CoverResource} REST controller.
 */
@SpringBootTest(classes = NidAuxHistoiresApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CoverResourceIT {

	private static final String DEFAULT_NAME = "AAAAAAAAAA";
	private static final String UPDATED_NAME = "BBBBBBBBBB";

	private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
	private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(1, "1");
	private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
	private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

	private static final byte[] DEFAULT_PREVIEW = TestUtil.createByteArray(1, "0");
	private static final byte[] UPDATED_PREVIEW = TestUtil.createByteArray(1, "1");
	private static final String DEFAULT_PREVIEW_CONTENT_TYPE = "image/jpg";
	private static final String UPDATED_PREVIEW_CONTENT_TYPE = "image/png";

	@Autowired
	private CoverRepository coverRepository;

	@Autowired
	private CoverMapper coverMapper;

	@Autowired
	private CoverService coverService;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restCoverMockMvc;

	private Cover cover;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Cover createEntity(EntityManager em) {
		Cover cover = new Cover().name(DEFAULT_NAME).picture(DEFAULT_PICTURE)
				.pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE).preview(DEFAULT_PREVIEW)
				.previewContentType(DEFAULT_PREVIEW_CONTENT_TYPE);
		return cover;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Cover createUpdatedEntity(EntityManager em) {
		Cover cover = new Cover().name(UPDATED_NAME).picture(UPDATED_PICTURE)
				.pictureContentType(UPDATED_PICTURE_CONTENT_TYPE).preview(UPDATED_PREVIEW)
				.previewContentType(UPDATED_PREVIEW_CONTENT_TYPE);
		return cover;
	}

	@BeforeEach
	public void initTest() {
		cover = createEntity(em);
	}

	@Test
	@Transactional
	public void createCover() throws Exception {
		int databaseSizeBeforeCreate = coverRepository.findAll().size();
		// Create the Cover
		CoverDTO coverDTO = coverMapper.toDto(cover);
		restCoverMockMvc.perform(post("/api/covers").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(coverDTO))).andExpect(status().isCreated());

		// Validate the Cover in the database
		List<Cover> coverList = coverRepository.findAll();
		assertThat(coverList).hasSize(databaseSizeBeforeCreate + 1);
		Cover testCover = coverList.get(coverList.size() - 1);
		assertThat(testCover.getName()).isEqualTo(DEFAULT_NAME);
		assertThat(testCover.getPicture()).isEqualTo(DEFAULT_PICTURE);
		assertThat(testCover.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
		assertThat(testCover.getPreview()).isEqualTo(DEFAULT_PREVIEW);
		assertThat(testCover.getPreviewContentType()).isEqualTo(DEFAULT_PREVIEW_CONTENT_TYPE);
	}

	@Test
	@Transactional
	public void createCoverWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = coverRepository.findAll().size();

		// Create the Cover with an existing ID
		cover.setId(1L);
		CoverDTO coverDTO = coverMapper.toDto(cover);

		// An entity with an existing ID cannot be created, so this API call must fail
		restCoverMockMvc.perform(post("/api/covers").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(coverDTO))).andExpect(status().isBadRequest());

		// Validate the Cover in the database
		List<Cover> coverList = coverRepository.findAll();
		assertThat(coverList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllCovers() throws Exception {
		// Initialize the database
		coverRepository.saveAndFlush(cover);

		// Get all the coverList
		restCoverMockMvc.perform(get("/api/covers?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(cover.getId().intValue())))
				.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
				.andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
				.andExpect(jsonPath("$.[*].picture").exists())
				.andExpect(jsonPath("$.[*].previewContentType").value(hasItem(DEFAULT_PREVIEW_CONTENT_TYPE)))
				.andExpect(jsonPath("$.[*].preview").value(hasItem(Base64Utils.encodeToString(DEFAULT_PREVIEW))));
	}

	@Test
	@Transactional
	public void getCover() throws Exception {
		// Initialize the database
		coverRepository.saveAndFlush(cover);

		// Get the cover
		restCoverMockMvc.perform(get("/api/covers/{id}", cover.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(cover.getId().intValue()))
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME))
				.andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
				.andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)))
				.andExpect(jsonPath("$.previewContentType").value(DEFAULT_PREVIEW_CONTENT_TYPE))
				.andExpect(jsonPath("$.preview").value(Base64Utils.encodeToString(DEFAULT_PREVIEW)));
	}

	@Test
	@Transactional
	public void getNonExistingCover() throws Exception {
		// Get the cover
		restCoverMockMvc.perform(get("/api/covers/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void updateCover() throws Exception {
		// Initialize the database
		coverRepository.saveAndFlush(cover);

		int databaseSizeBeforeUpdate = coverRepository.findAll().size();

		// Update the cover
		Cover updatedCover = coverRepository.findById(cover.getId()).get();
		// Disconnect from session so that the updates on updatedCover are not directly
		// saved in db
		em.detach(updatedCover);
		updatedCover.name(UPDATED_NAME).picture(UPDATED_PICTURE).pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
				.preview(UPDATED_PREVIEW).previewContentType(UPDATED_PREVIEW_CONTENT_TYPE);
		CoverDTO coverDTO = coverMapper.toDto(updatedCover);

		restCoverMockMvc.perform(put("/api/covers").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(coverDTO))).andExpect(status().isOk());

		// Validate the Cover in the database
		List<Cover> coverList = coverRepository.findAll();
		assertThat(coverList).hasSize(databaseSizeBeforeUpdate);
		Cover testCover = coverList.get(coverList.size() - 1);
		assertThat(testCover.getName()).isEqualTo(UPDATED_NAME);
		assertThat(testCover.getPicture()).isEqualTo(UPDATED_PICTURE);
		assertThat(testCover.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
		assertThat(testCover.getPreview()).isEqualTo(UPDATED_PREVIEW);
		assertThat(testCover.getPreviewContentType()).isEqualTo(UPDATED_PREVIEW_CONTENT_TYPE);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void updateNonExistingCover() throws Exception {
		int databaseSizeBeforeUpdate = coverRepository.findAll().size();

		// Create the Cover
		CoverDTO coverDTO = coverMapper.toDto(cover);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restCoverMockMvc.perform(put("/api/covers").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(coverDTO))).andExpect(status().is4xxClientError());

		// Validate the Cover in the database
		List<Cover> coverList = coverRepository.findAll();
		assertThat(coverList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void deleteCover() throws Exception {
		// Initialize the database
		coverRepository.saveAndFlush(cover);

		int databaseSizeBeforeDelete = coverRepository.findAll().size();

		// Delete the cover
		restCoverMockMvc.perform(delete("/api/covers/{id}", cover.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Cover> coverList = coverRepository.findAll();
		assertThat(coverList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
