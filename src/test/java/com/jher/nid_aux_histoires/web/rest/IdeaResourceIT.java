package com.jher.nid_aux_histoires.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jher.nid_aux_histoires.IntegrationTest;
import com.jher.nid_aux_histoires.domain.Idea;
import com.jher.nid_aux_histoires.repository.IdeaRepository;
import com.jher.nid_aux_histoires.service.dto.IdeaDTO;
import com.jher.nid_aux_histoires.service.mapper.IdeaMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IdeaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IdeaResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ideas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private IdeaMapper ideaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdeaMockMvc;

    private Idea idea;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Idea createEntity(EntityManager em) {
        Idea idea = new Idea().type(DEFAULT_TYPE).value(DEFAULT_VALUE).complement(DEFAULT_COMPLEMENT);
        return idea;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
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
    void createIdea() throws Exception {
        int databaseSizeBeforeCreate = ideaRepository.findAll().size();
        // Create the Idea
        IdeaDTO ideaDTO = ideaMapper.toDto(idea);
        restIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ideaDTO)))
            .andExpect(status().isCreated());

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
    void createIdeaWithExistingId() throws Exception {
        // Create the Idea with an existing ID
        idea.setId(1L);
        IdeaDTO ideaDTO = ideaMapper.toDto(idea);

        int databaseSizeBeforeCreate = ideaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdeaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ideaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIdeas() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        // Get all the ideaList
        restIdeaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(idea.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].complement").value(hasItem(DEFAULT_COMPLEMENT)));
    }

    @Test
    @Transactional
    void getIdea() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        // Get the idea
        restIdeaMockMvc
            .perform(get(ENTITY_API_URL_ID, idea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(idea.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.complement").value(DEFAULT_COMPLEMENT));
    }

    @Test
    @Transactional
    void getNonExistingIdea() throws Exception {
        // Get the idea
        restIdeaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIdea() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

        // Update the idea
        Idea updatedIdea = ideaRepository.findById(idea.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIdea are not directly saved in db
        em.detach(updatedIdea);
        updatedIdea.type(UPDATED_TYPE).value(UPDATED_VALUE).complement(UPDATED_COMPLEMENT);
        IdeaDTO ideaDTO = ideaMapper.toDto(updatedIdea);

        restIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ideaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ideaDTO))
            )
            .andExpect(status().isOk());

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
    void putNonExistingIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();
        idea.setId(longCount.incrementAndGet());

        // Create the Idea
        IdeaDTO ideaDTO = ideaMapper.toDto(idea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ideaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ideaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();
        idea.setId(longCount.incrementAndGet());

        // Create the Idea
        IdeaDTO ideaDTO = ideaMapper.toDto(idea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdeaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ideaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();
        idea.setId(longCount.incrementAndGet());

        // Create the Idea
        IdeaDTO ideaDTO = ideaMapper.toDto(idea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdeaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ideaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIdeaWithPatch() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

        // Update the idea using partial update
        Idea partialUpdatedIdea = new Idea();
        partialUpdatedIdea.setId(idea.getId());

        partialUpdatedIdea.value(UPDATED_VALUE);

        restIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdea))
            )
            .andExpect(status().isOk());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
        Idea testIdea = ideaList.get(ideaList.size() - 1);
        assertThat(testIdea.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testIdea.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIdea.getComplement()).isEqualTo(DEFAULT_COMPLEMENT);
    }

    @Test
    @Transactional
    void fullUpdateIdeaWithPatch() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();

        // Update the idea using partial update
        Idea partialUpdatedIdea = new Idea();
        partialUpdatedIdea.setId(idea.getId());

        partialUpdatedIdea.type(UPDATED_TYPE).value(UPDATED_VALUE).complement(UPDATED_COMPLEMENT);

        restIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdea.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdea))
            )
            .andExpect(status().isOk());

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
    void patchNonExistingIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();
        idea.setId(longCount.incrementAndGet());

        // Create the Idea
        IdeaDTO ideaDTO = ideaMapper.toDto(idea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ideaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ideaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();
        idea.setId(longCount.incrementAndGet());

        // Create the Idea
        IdeaDTO ideaDTO = ideaMapper.toDto(idea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdeaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ideaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIdea() throws Exception {
        int databaseSizeBeforeUpdate = ideaRepository.findAll().size();
        idea.setId(longCount.incrementAndGet());

        // Create the Idea
        IdeaDTO ideaDTO = ideaMapper.toDto(idea);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdeaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ideaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Idea in the database
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIdea() throws Exception {
        // Initialize the database
        ideaRepository.saveAndFlush(idea);

        int databaseSizeBeforeDelete = ideaRepository.findAll().size();

        // Delete the idea
        restIdeaMockMvc
            .perform(delete(ENTITY_API_URL_ID, idea.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Idea> ideaList = ideaRepository.findAll();
        assertThat(ideaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
