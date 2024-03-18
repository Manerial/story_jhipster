package com.jher.nid_aux_histoires.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jher.nid_aux_histoires.IntegrationTest;
import com.jher.nid_aux_histoires.domain.WordAnalysis;
import com.jher.nid_aux_histoires.repository.WordAnalysisRepository;
import com.jher.nid_aux_histoires.service.dto.WordAnalysisDTO;
import com.jher.nid_aux_histoires.service.mapper.WordAnalysisMapper;
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
 * Integration tests for the {@link WordAnalysisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WordAnalysisResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ANALYSIS = "AAAAAAAAAA";
    private static final String UPDATED_ANALYSIS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/word-analyses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WordAnalysisRepository wordAnalysisRepository;

    @Autowired
    private WordAnalysisMapper wordAnalysisMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWordAnalysisMockMvc;

    private WordAnalysis wordAnalysis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WordAnalysis createEntity(EntityManager em) {
        WordAnalysis wordAnalysis = new WordAnalysis().type(DEFAULT_TYPE).name(DEFAULT_NAME).analysis(DEFAULT_ANALYSIS);
        return wordAnalysis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WordAnalysis createUpdatedEntity(EntityManager em) {
        WordAnalysis wordAnalysis = new WordAnalysis().type(UPDATED_TYPE).name(UPDATED_NAME).analysis(UPDATED_ANALYSIS);
        return wordAnalysis;
    }

    @BeforeEach
    public void initTest() {
        wordAnalysis = createEntity(em);
    }

    @Test
    @Transactional
    void createWordAnalysis() throws Exception {
        int databaseSizeBeforeCreate = wordAnalysisRepository.findAll().size();
        // Create the WordAnalysis
        WordAnalysisDTO wordAnalysisDTO = wordAnalysisMapper.toDto(wordAnalysis);
        restWordAnalysisMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wordAnalysisDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WordAnalysis in the database
        List<WordAnalysis> wordAnalysisList = wordAnalysisRepository.findAll();
        assertThat(wordAnalysisList).hasSize(databaseSizeBeforeCreate + 1);
        WordAnalysis testWordAnalysis = wordAnalysisList.get(wordAnalysisList.size() - 1);
        assertThat(testWordAnalysis.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testWordAnalysis.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWordAnalysis.getAnalysis()).isEqualTo(DEFAULT_ANALYSIS);
    }

    @Test
    @Transactional
    void createWordAnalysisWithExistingId() throws Exception {
        // Create the WordAnalysis with an existing ID
        wordAnalysis.setId(1L);
        WordAnalysisDTO wordAnalysisDTO = wordAnalysisMapper.toDto(wordAnalysis);

        int databaseSizeBeforeCreate = wordAnalysisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWordAnalysisMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wordAnalysisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WordAnalysis in the database
        List<WordAnalysis> wordAnalysisList = wordAnalysisRepository.findAll();
        assertThat(wordAnalysisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWordAnalyses() throws Exception {
        // Initialize the database
        wordAnalysisRepository.saveAndFlush(wordAnalysis);

        // Get all the wordAnalysisList
        restWordAnalysisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wordAnalysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].analysis").value(hasItem(DEFAULT_ANALYSIS)));
    }

    @Test
    @Transactional
    void getWordAnalysis() throws Exception {
        // Initialize the database
        wordAnalysisRepository.saveAndFlush(wordAnalysis);

        // Get the wordAnalysis
        restWordAnalysisMockMvc
            .perform(get(ENTITY_API_URL_ID, wordAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wordAnalysis.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.analysis").value(DEFAULT_ANALYSIS));
    }

    @Test
    @Transactional
    void getNonExistingWordAnalysis() throws Exception {
        // Get the wordAnalysis
        restWordAnalysisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWordAnalysis() throws Exception {
        // Initialize the database
        wordAnalysisRepository.saveAndFlush(wordAnalysis);

        int databaseSizeBeforeUpdate = wordAnalysisRepository.findAll().size();

        // Update the wordAnalysis
        WordAnalysis updatedWordAnalysis = wordAnalysisRepository.findById(wordAnalysis.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWordAnalysis are not directly saved in db
        em.detach(updatedWordAnalysis);
        updatedWordAnalysis.type(UPDATED_TYPE).name(UPDATED_NAME).analysis(UPDATED_ANALYSIS);
        WordAnalysisDTO wordAnalysisDTO = wordAnalysisMapper.toDto(updatedWordAnalysis);

        restWordAnalysisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wordAnalysisDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wordAnalysisDTO))
            )
            .andExpect(status().isOk());

        // Validate the WordAnalysis in the database
        List<WordAnalysis> wordAnalysisList = wordAnalysisRepository.findAll();
        assertThat(wordAnalysisList).hasSize(databaseSizeBeforeUpdate);
        WordAnalysis testWordAnalysis = wordAnalysisList.get(wordAnalysisList.size() - 1);
        assertThat(testWordAnalysis.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testWordAnalysis.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWordAnalysis.getAnalysis()).isEqualTo(UPDATED_ANALYSIS);
    }

    @Test
    @Transactional
    void putNonExistingWordAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = wordAnalysisRepository.findAll().size();
        wordAnalysis.setId(longCount.incrementAndGet());

        // Create the WordAnalysis
        WordAnalysisDTO wordAnalysisDTO = wordAnalysisMapper.toDto(wordAnalysis);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWordAnalysisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wordAnalysisDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wordAnalysisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WordAnalysis in the database
        List<WordAnalysis> wordAnalysisList = wordAnalysisRepository.findAll();
        assertThat(wordAnalysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWordAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = wordAnalysisRepository.findAll().size();
        wordAnalysis.setId(longCount.incrementAndGet());

        // Create the WordAnalysis
        WordAnalysisDTO wordAnalysisDTO = wordAnalysisMapper.toDto(wordAnalysis);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWordAnalysisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wordAnalysisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WordAnalysis in the database
        List<WordAnalysis> wordAnalysisList = wordAnalysisRepository.findAll();
        assertThat(wordAnalysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWordAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = wordAnalysisRepository.findAll().size();
        wordAnalysis.setId(longCount.incrementAndGet());

        // Create the WordAnalysis
        WordAnalysisDTO wordAnalysisDTO = wordAnalysisMapper.toDto(wordAnalysis);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWordAnalysisMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wordAnalysisDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WordAnalysis in the database
        List<WordAnalysis> wordAnalysisList = wordAnalysisRepository.findAll();
        assertThat(wordAnalysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWordAnalysisWithPatch() throws Exception {
        // Initialize the database
        wordAnalysisRepository.saveAndFlush(wordAnalysis);

        int databaseSizeBeforeUpdate = wordAnalysisRepository.findAll().size();

        // Update the wordAnalysis using partial update
        WordAnalysis partialUpdatedWordAnalysis = new WordAnalysis();
        partialUpdatedWordAnalysis.setId(wordAnalysis.getId());

        partialUpdatedWordAnalysis.type(UPDATED_TYPE).name(UPDATED_NAME);

        restWordAnalysisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWordAnalysis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWordAnalysis))
            )
            .andExpect(status().isOk());

        // Validate the WordAnalysis in the database
        List<WordAnalysis> wordAnalysisList = wordAnalysisRepository.findAll();
        assertThat(wordAnalysisList).hasSize(databaseSizeBeforeUpdate);
        WordAnalysis testWordAnalysis = wordAnalysisList.get(wordAnalysisList.size() - 1);
        assertThat(testWordAnalysis.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testWordAnalysis.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWordAnalysis.getAnalysis()).isEqualTo(DEFAULT_ANALYSIS);
    }

    @Test
    @Transactional
    void fullUpdateWordAnalysisWithPatch() throws Exception {
        // Initialize the database
        wordAnalysisRepository.saveAndFlush(wordAnalysis);

        int databaseSizeBeforeUpdate = wordAnalysisRepository.findAll().size();

        // Update the wordAnalysis using partial update
        WordAnalysis partialUpdatedWordAnalysis = new WordAnalysis();
        partialUpdatedWordAnalysis.setId(wordAnalysis.getId());

        partialUpdatedWordAnalysis.type(UPDATED_TYPE).name(UPDATED_NAME).analysis(UPDATED_ANALYSIS);

        restWordAnalysisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWordAnalysis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWordAnalysis))
            )
            .andExpect(status().isOk());

        // Validate the WordAnalysis in the database
        List<WordAnalysis> wordAnalysisList = wordAnalysisRepository.findAll();
        assertThat(wordAnalysisList).hasSize(databaseSizeBeforeUpdate);
        WordAnalysis testWordAnalysis = wordAnalysisList.get(wordAnalysisList.size() - 1);
        assertThat(testWordAnalysis.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testWordAnalysis.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWordAnalysis.getAnalysis()).isEqualTo(UPDATED_ANALYSIS);
    }

    @Test
    @Transactional
    void patchNonExistingWordAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = wordAnalysisRepository.findAll().size();
        wordAnalysis.setId(longCount.incrementAndGet());

        // Create the WordAnalysis
        WordAnalysisDTO wordAnalysisDTO = wordAnalysisMapper.toDto(wordAnalysis);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWordAnalysisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wordAnalysisDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wordAnalysisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WordAnalysis in the database
        List<WordAnalysis> wordAnalysisList = wordAnalysisRepository.findAll();
        assertThat(wordAnalysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWordAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = wordAnalysisRepository.findAll().size();
        wordAnalysis.setId(longCount.incrementAndGet());

        // Create the WordAnalysis
        WordAnalysisDTO wordAnalysisDTO = wordAnalysisMapper.toDto(wordAnalysis);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWordAnalysisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wordAnalysisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WordAnalysis in the database
        List<WordAnalysis> wordAnalysisList = wordAnalysisRepository.findAll();
        assertThat(wordAnalysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWordAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = wordAnalysisRepository.findAll().size();
        wordAnalysis.setId(longCount.incrementAndGet());

        // Create the WordAnalysis
        WordAnalysisDTO wordAnalysisDTO = wordAnalysisMapper.toDto(wordAnalysis);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWordAnalysisMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wordAnalysisDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WordAnalysis in the database
        List<WordAnalysis> wordAnalysisList = wordAnalysisRepository.findAll();
        assertThat(wordAnalysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWordAnalysis() throws Exception {
        // Initialize the database
        wordAnalysisRepository.saveAndFlush(wordAnalysis);

        int databaseSizeBeforeDelete = wordAnalysisRepository.findAll().size();

        // Delete the wordAnalysis
        restWordAnalysisMockMvc
            .perform(delete(ENTITY_API_URL_ID, wordAnalysis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WordAnalysis> wordAnalysisList = wordAnalysisRepository.findAll();
        assertThat(wordAnalysisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
