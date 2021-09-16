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
import com.jher.nid_aux_histoires.domain.Idea;
import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.security.AuthoritiesConstants;
import com.jher.nid_aux_histoires.service.IdeaService;
import com.jher.nid_aux_histoires.service.dto.IdeaDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_CreatureDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_LocationDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_ObjectDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_PersonaDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_WritingOptionDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.Random_Interface;
import com.jher.nid_aux_histoires.service.mapper.IdeaMapper;
import com.jher.nid_aux_histoires.service.tool.REG_Entity;

/**
 * Integration tests for the {@link IdeaResource} REST controller.
 */
@SpringBootTest(classes = NidAuxHistoiresApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IdeaResourceIT {

	private static final String DEFAULT_TYPE = "AAAAAAAAAA";
	private static final String UPDATED_TYPE = "BBBBBBBBBB";

	private static final String DEFAULT_VALUE = "AAAAAAAAAA";
	private static final String UPDATED_VALUE = "BBBBBBBBBB";

	private static final String DEFAULT_COMPLEMENT = "AAAAAAAAAA";
	private static final String UPDATED_COMPLEMENT = "BBBBBBBBBB";

	private static final int GENERATIONS = 200;

	@Autowired
	private IdeaRepository ideaRepository;

	@Autowired
	private IdeaMapper ideaMapper;

	@Autowired
	private IdeaService ideaService;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restIdeaMockMvc;

	private Idea idea;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Idea createEntity(EntityManager em) {
		Idea idea = new Idea().type(DEFAULT_TYPE).value(DEFAULT_VALUE).complement(DEFAULT_COMPLEMENT);
		return idea;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Idea createUpdatedEntity(EntityManager em) {
		Idea idea = new Idea().type(UPDATED_TYPE).value(UPDATED_VALUE).complement(UPDATED_COMPLEMENT);
		return idea;
	}

	@BeforeEach
	public void initTest() {
		idea = createEntity(em);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void createIdea() throws Exception {
		int databaseSizeBeforeCreate = ideaRepository.findAll().size();
		// Create the Idea
		IdeaDTO ideaDTO = ideaMapper.toDto(idea);
		restIdeaMockMvc.perform(post("/api/ideas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(ideaDTO))).andExpect(status().isCreated());

		// Validate the Idea in the database
		List<Idea> ideaList = ideaRepository.findAll();
		assertThat(ideaList).hasSize(databaseSizeBeforeCreate + 1);
		Idea testIdea = ideaList.get(ideaList.size() - 1);
		assertThat(testIdea.getType()).isEqualTo(DEFAULT_TYPE);
		assertThat(testIdea.getValue()).isEqualTo(DEFAULT_VALUE);
		assertThat(testIdea.getComplement()).isEqualTo(DEFAULT_COMPLEMENT);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void createIdeaWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = ideaRepository.findAll().size();

		// Create the Idea with an existing ID
		idea.setId(1L);
		IdeaDTO ideaDTO = ideaMapper.toDto(idea);

		// An entity with an existing ID cannot be created, so this API call must fail
		restIdeaMockMvc.perform(post("/api/ideas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(ideaDTO))).andExpect(status().isBadRequest());

		// Validate the Idea in the database
		List<Idea> ideaList = ideaRepository.findAll();
		assertThat(ideaList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void getAllIdeas() throws Exception {
		// Initialize the database
		ideaRepository.saveAndFlush(idea);

		// Get all the ideaList
		restIdeaMockMvc.perform(get("/api/ideas?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(idea.getId().intValue())))
				.andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
				.andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
				.andExpect(jsonPath("$.[*].complement").value(hasItem(DEFAULT_COMPLEMENT)));
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void getIdea() throws Exception {
		// Initialize the database
		ideaRepository.saveAndFlush(idea);

		// Get the idea
		restIdeaMockMvc.perform(get("/api/ideas/{id}", idea.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(idea.getId().intValue()))
				.andExpect(jsonPath("$.type").value(DEFAULT_TYPE)).andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
				.andExpect(jsonPath("$.complement").value(DEFAULT_COMPLEMENT));
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void getNonExistingIdea() throws Exception {
		// Get the idea
		restIdeaMockMvc.perform(get("/api/ideas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void updateIdea() throws Exception {
		// Initialize the database
		ideaRepository.saveAndFlush(idea);

		int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

		// Update the idea
		Idea updatedIdea = ideaRepository.findById(idea.getId()).get();
		// Disconnect from session so that the updates on updatedIdea are not directly
		// saved in db
		em.detach(updatedIdea);
		updatedIdea.type(UPDATED_TYPE).value(UPDATED_VALUE).complement(UPDATED_COMPLEMENT);
		IdeaDTO ideaDTO = ideaMapper.toDto(updatedIdea);

		restIdeaMockMvc.perform(put("/api/ideas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(ideaDTO))).andExpect(status().isOk());

		// Validate the Idea in the database
		List<Idea> ideaList = ideaRepository.findAll();
		assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
		Idea testIdea = ideaList.get(ideaList.size() - 1);
		assertThat(testIdea.getType()).isEqualTo(UPDATED_TYPE);
		assertThat(testIdea.getValue()).isEqualTo(UPDATED_VALUE);
		assertThat(testIdea.getComplement()).isEqualTo(UPDATED_COMPLEMENT);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void updateNonExistingIdea() throws Exception {
		int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

		// Create the Idea
		IdeaDTO ideaDTO = ideaMapper.toDto(idea);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restIdeaMockMvc.perform(put("/api/ideas").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(ideaDTO))).andExpect(status().is4xxClientError());

		// Validate the Idea in the database
		List<Idea> ideaList = ideaRepository.findAll();
		assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	@WithMockUser(username = "admin", authorities = { AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public void deleteIdea() throws Exception {
		// Initialize the database
		ideaRepository.saveAndFlush(idea);

		int databaseSizeBeforeDelete = ideaRepository.findAll().size();

		// Delete the idea
		restIdeaMockMvc.perform(delete("/api/ideas/{id}", idea.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Idea> ideaList = ideaRepository.findAll();
		assertThat(ideaList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	public void createRandomPersona() {
		List<Random_Interface> list = ideaService.generate(GENERATIONS, REG_Entity.persona, new R_PersonaDTO());
		assertThat(list).hasSize(GENERATIONS);
	}

	@Test
	public void createRandomHonoraryTitle() {
		List<Random_Interface> list = ideaService.generate(GENERATIONS, REG_Entity.honorary_title, new R_ObjectDTO());
		assertThat(list).hasSize(GENERATIONS);
	}

	@Test
	public void createRandomLocation() {
		List<Random_Interface> list = ideaService.generate(GENERATIONS, REG_Entity.location, new R_LocationDTO());
		assertThat(list).hasSize(GENERATIONS);
	}

	@Test
	public void createRandomObject() {
		List<Random_Interface> list = ideaService.generate(GENERATIONS, REG_Entity.object, new R_ObjectDTO());
		assertThat(list).hasSize(GENERATIONS);
	}

	@Test
	public void createRandomWritingOption() {
		List<Random_Interface> list = ideaService.generate(GENERATIONS, REG_Entity.writing_option,
				new R_WritingOptionDTO());
		assertThat(list).hasSize(GENERATIONS);
	}

	@Test
	public void createRandomCreature() {
		List<Random_Interface> list = ideaService.generate(GENERATIONS, REG_Entity.creature, new R_CreatureDTO());
		assertThat(list).hasSize(GENERATIONS);
	}
}
