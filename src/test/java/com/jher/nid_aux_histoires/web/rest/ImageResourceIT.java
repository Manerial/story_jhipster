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
import com.jher.nid_aux_histoires.domain.Image;
import com.jher.nid_aux_histoires.repository.ImageRepository;
import com.jher.nid_aux_histoires.security.AuthoritiesConstants;
import com.jher.nid_aux_histoires.service.ImageService;
import com.jher.nid_aux_histoires.service.dto.ImageDTO;
import com.jher.nid_aux_histoires.service.mapper.ImageMapper;

/**
 * Integration tests for the {@link ImageResource} REST controller.
 */
@SpringBootTest(classes = NidAuxHistoiresApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ImageResourceIT {

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
	private ImageRepository imageRepository;

	@Autowired
	private ImageMapper imageMapper;

	@Autowired
	private ImageService imageService;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restImageMockMvc;

	private Image image;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Image createEntity(EntityManager em) {
		Image image = new Image().name(DEFAULT_NAME).picture(DEFAULT_PICTURE)
				.pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE).preview(DEFAULT_PREVIEW)
				.previewContentType(DEFAULT_PREVIEW_CONTENT_TYPE);
		return image;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Image createUpdatedEntity(EntityManager em) {
		Image image = new Image().name(UPDATED_NAME).picture(UPDATED_PICTURE)
				.pictureContentType(UPDATED_PICTURE_CONTENT_TYPE).preview(UPDATED_PREVIEW)
				.previewContentType(UPDATED_PREVIEW_CONTENT_TYPE);
		return image;
	}

	@BeforeEach
	public void initTest() {
		image = createEntity(em);
	}

	@Test
	@Transactional
	public void createImage() throws Exception {
		int databaseSizeBeforeCreate = imageRepository.findAll().size();
		// Create the Image
		ImageDTO imageDTO = imageMapper.toDto(image);
		restImageMockMvc.perform(post("/api/images").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(imageDTO))).andExpect(status().isCreated());

		// Validate the Image in the database
		List<Image> imageList = imageRepository.findAll();
		assertThat(imageList).hasSize(databaseSizeBeforeCreate + 1);
		Image testImage = imageList.get(imageList.size() - 1);
		assertThat(testImage.getName()).isEqualTo(DEFAULT_NAME);
		assertThat(testImage.getPicture()).isEqualTo(DEFAULT_PICTURE);
		assertThat(testImage.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
		assertThat(testImage.getPreview()).isEqualTo(DEFAULT_PREVIEW);
		assertThat(testImage.getPreviewContentType()).isEqualTo(DEFAULT_PREVIEW_CONTENT_TYPE);
	}

	@Test
	@Transactional
	public void createImageWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = imageRepository.findAll().size();

		// Create the Image with an existing ID
		image.setId(1L);
		ImageDTO imageDTO = imageMapper.toDto(image);

		// An entity with an existing ID cannot be created, so this API call must fail
		restImageMockMvc.perform(post("/api/images").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(imageDTO))).andExpect(status().isBadRequest());

		// Validate the Image in the database
		List<Image> imageList = imageRepository.findAll();
		assertThat(imageList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllImages() throws Exception {
		// Initialize the database
		imageRepository.saveAndFlush(image);

		// Get all the imageList
		restImageMockMvc.perform(get("/api/images?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(image.getId().intValue())))
				.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
				.andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
				.andExpect(jsonPath("$.[*].picture").exists())
				.andExpect(jsonPath("$.[*].previewContentType").value(hasItem(DEFAULT_PREVIEW_CONTENT_TYPE)))
				.andExpect(jsonPath("$.[*].preview").value(hasItem(Base64Utils.encodeToString(DEFAULT_PREVIEW))));
	}

	@Test
	@Transactional
	public void getImage() throws Exception {
		// Initialize the database
		imageRepository.saveAndFlush(image);

		// Get the image
		restImageMockMvc.perform(get("/api/images/{id}", image.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(image.getId().intValue()))
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME))
				.andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
				.andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)))
				.andExpect(jsonPath("$.previewContentType").value(DEFAULT_PREVIEW_CONTENT_TYPE))
				.andExpect(jsonPath("$.preview").value(Base64Utils.encodeToString(DEFAULT_PREVIEW)));
	}

	@Test
	@Transactional
	public void getNonExistingImage() throws Exception {
		// Get the image
		restImageMockMvc.perform(get("/api/images/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void updateImage() throws Exception {
		// Initialize the database
		imageRepository.saveAndFlush(image);

		int databaseSizeBeforeUpdate = imageRepository.findAll().size();

		// Update the image
		Image updatedImage = imageRepository.findById(image.getId()).get();
		// Disconnect from session so that the updates on updatedImage are not directly
		// saved in db
		em.detach(updatedImage);
		updatedImage.name(UPDATED_NAME).picture(UPDATED_PICTURE).pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
				.preview(UPDATED_PREVIEW).previewContentType(UPDATED_PREVIEW_CONTENT_TYPE);
		ImageDTO imageDTO = imageMapper.toDto(updatedImage);

		restImageMockMvc.perform(put("/api/images").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(imageDTO))).andExpect(status().isOk());

		// Validate the Image in the database
		List<Image> imageList = imageRepository.findAll();
		assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
		Image testImage = imageList.get(imageList.size() - 1);
		assertThat(testImage.getName()).isEqualTo(UPDATED_NAME);
		assertThat(testImage.getPicture()).isEqualTo(UPDATED_PICTURE);
		assertThat(testImage.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
		assertThat(testImage.getPreview()).isEqualTo(UPDATED_PREVIEW);
		assertThat(testImage.getPreviewContentType()).isEqualTo(UPDATED_PREVIEW_CONTENT_TYPE);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void updateNonExistingImage() throws Exception {
		int databaseSizeBeforeUpdate = imageRepository.findAll().size();

		// Create the Image
		ImageDTO imageDTO = imageMapper.toDto(image);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restImageMockMvc.perform(put("/api/images").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(imageDTO))).andExpect(status().is4xxClientError());

		// Validate the Image in the database
		List<Image> imageList = imageRepository.findAll();
		assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void deleteImage() throws Exception {
		// Initialize the database
		imageRepository.saveAndFlush(image);

		int databaseSizeBeforeDelete = imageRepository.findAll().size();

		// Delete the image
		restImageMockMvc.perform(delete("/api/images/{id}", image.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Image> imageList = imageRepository.findAll();
		assertThat(imageList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
