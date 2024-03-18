package com.jher.nid_aux_histoires.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jher.nid_aux_histoires.IntegrationTest;
import com.jher.nid_aux_histoires.domain.BookStatus;
import com.jher.nid_aux_histoires.repository.BookStatusRepository;
import com.jher.nid_aux_histoires.service.dto.BookStatusDTO;
import com.jher.nid_aux_histoires.service.mapper.BookStatusMapper;
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
 * Integration tests for the {@link BookStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BookStatusResourceIT {

    private static final Boolean DEFAULT_FINISHED = false;
    private static final Boolean UPDATED_FINISHED = true;

    private static final Boolean DEFAULT_FAVORIT = false;
    private static final Boolean UPDATED_FAVORIT = true;

    private static final String ENTITY_API_URL = "/api/book-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookStatusRepository bookStatusRepository;

    @Autowired
    private BookStatusMapper bookStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookStatusMockMvc;

    private BookStatus bookStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookStatus createEntity(EntityManager em) {
        BookStatus bookStatus = new BookStatus().finished(DEFAULT_FINISHED).favorit(DEFAULT_FAVORIT);
        return bookStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
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
    void createBookStatus() throws Exception {
        int databaseSizeBeforeCreate = bookStatusRepository.findAll().size();
        // Create the BookStatus
        BookStatusDTO bookStatusDTO = bookStatusMapper.toDto(bookStatus);
        restBookStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the BookStatus in the database
        List<BookStatus> bookStatusList = bookStatusRepository.findAll();
        assertThat(bookStatusList).hasSize(databaseSizeBeforeCreate + 1);
        BookStatus testBookStatus = bookStatusList.get(bookStatusList.size() - 1);
        assertThat(testBookStatus.getFinished()).isEqualTo(DEFAULT_FINISHED);
        assertThat(testBookStatus.getFavorit()).isEqualTo(DEFAULT_FAVORIT);
    }

    @Test
    @Transactional
    void createBookStatusWithExistingId() throws Exception {
        // Create the BookStatus with an existing ID
        bookStatus.setId(1L);
        BookStatusDTO bookStatusDTO = bookStatusMapper.toDto(bookStatus);

        int databaseSizeBeforeCreate = bookStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookStatus in the database
        List<BookStatus> bookStatusList = bookStatusRepository.findAll();
        assertThat(bookStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBookStatuses() throws Exception {
        // Initialize the database
        bookStatusRepository.saveAndFlush(bookStatus);

        // Get all the bookStatusList
        restBookStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].finished").value(hasItem(DEFAULT_FINISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].favorit").value(hasItem(DEFAULT_FAVORIT.booleanValue())));
    }

    @Test
    @Transactional
    void getBookStatus() throws Exception {
        // Initialize the database
        bookStatusRepository.saveAndFlush(bookStatus);

        // Get the bookStatus
        restBookStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, bookStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookStatus.getId().intValue()))
            .andExpect(jsonPath("$.finished").value(DEFAULT_FINISHED.booleanValue()))
            .andExpect(jsonPath("$.favorit").value(DEFAULT_FAVORIT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBookStatus() throws Exception {
        // Get the bookStatus
        restBookStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBookStatus() throws Exception {
        // Initialize the database
        bookStatusRepository.saveAndFlush(bookStatus);

        int databaseSizeBeforeUpdate = bookStatusRepository.findAll().size();

        // Update the bookStatus
        BookStatus updatedBookStatus = bookStatusRepository.findById(bookStatus.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBookStatus are not directly saved in db
        em.detach(updatedBookStatus);
        updatedBookStatus.finished(UPDATED_FINISHED).favorit(UPDATED_FAVORIT);
        BookStatusDTO bookStatusDTO = bookStatusMapper.toDto(updatedBookStatus);

        restBookStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the BookStatus in the database
        List<BookStatus> bookStatusList = bookStatusRepository.findAll();
        assertThat(bookStatusList).hasSize(databaseSizeBeforeUpdate);
        BookStatus testBookStatus = bookStatusList.get(bookStatusList.size() - 1);
        assertThat(testBookStatus.getFinished()).isEqualTo(UPDATED_FINISHED);
        assertThat(testBookStatus.getFavorit()).isEqualTo(UPDATED_FAVORIT);
    }

    @Test
    @Transactional
    void putNonExistingBookStatus() throws Exception {
        int databaseSizeBeforeUpdate = bookStatusRepository.findAll().size();
        bookStatus.setId(longCount.incrementAndGet());

        // Create the BookStatus
        BookStatusDTO bookStatusDTO = bookStatusMapper.toDto(bookStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookStatus in the database
        List<BookStatus> bookStatusList = bookStatusRepository.findAll();
        assertThat(bookStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBookStatus() throws Exception {
        int databaseSizeBeforeUpdate = bookStatusRepository.findAll().size();
        bookStatus.setId(longCount.incrementAndGet());

        // Create the BookStatus
        BookStatusDTO bookStatusDTO = bookStatusMapper.toDto(bookStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookStatus in the database
        List<BookStatus> bookStatusList = bookStatusRepository.findAll();
        assertThat(bookStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBookStatus() throws Exception {
        int databaseSizeBeforeUpdate = bookStatusRepository.findAll().size();
        bookStatus.setId(longCount.incrementAndGet());

        // Create the BookStatus
        BookStatusDTO bookStatusDTO = bookStatusMapper.toDto(bookStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookStatusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookStatus in the database
        List<BookStatus> bookStatusList = bookStatusRepository.findAll();
        assertThat(bookStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBookStatusWithPatch() throws Exception {
        // Initialize the database
        bookStatusRepository.saveAndFlush(bookStatus);

        int databaseSizeBeforeUpdate = bookStatusRepository.findAll().size();

        // Update the bookStatus using partial update
        BookStatus partialUpdatedBookStatus = new BookStatus();
        partialUpdatedBookStatus.setId(bookStatus.getId());

        partialUpdatedBookStatus.finished(UPDATED_FINISHED);

        restBookStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookStatus))
            )
            .andExpect(status().isOk());

        // Validate the BookStatus in the database
        List<BookStatus> bookStatusList = bookStatusRepository.findAll();
        assertThat(bookStatusList).hasSize(databaseSizeBeforeUpdate);
        BookStatus testBookStatus = bookStatusList.get(bookStatusList.size() - 1);
        assertThat(testBookStatus.getFinished()).isEqualTo(UPDATED_FINISHED);
        assertThat(testBookStatus.getFavorit()).isEqualTo(DEFAULT_FAVORIT);
    }

    @Test
    @Transactional
    void fullUpdateBookStatusWithPatch() throws Exception {
        // Initialize the database
        bookStatusRepository.saveAndFlush(bookStatus);

        int databaseSizeBeforeUpdate = bookStatusRepository.findAll().size();

        // Update the bookStatus using partial update
        BookStatus partialUpdatedBookStatus = new BookStatus();
        partialUpdatedBookStatus.setId(bookStatus.getId());

        partialUpdatedBookStatus.finished(UPDATED_FINISHED).favorit(UPDATED_FAVORIT);

        restBookStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookStatus))
            )
            .andExpect(status().isOk());

        // Validate the BookStatus in the database
        List<BookStatus> bookStatusList = bookStatusRepository.findAll();
        assertThat(bookStatusList).hasSize(databaseSizeBeforeUpdate);
        BookStatus testBookStatus = bookStatusList.get(bookStatusList.size() - 1);
        assertThat(testBookStatus.getFinished()).isEqualTo(UPDATED_FINISHED);
        assertThat(testBookStatus.getFavorit()).isEqualTo(UPDATED_FAVORIT);
    }

    @Test
    @Transactional
    void patchNonExistingBookStatus() throws Exception {
        int databaseSizeBeforeUpdate = bookStatusRepository.findAll().size();
        bookStatus.setId(longCount.incrementAndGet());

        // Create the BookStatus
        BookStatusDTO bookStatusDTO = bookStatusMapper.toDto(bookStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookStatus in the database
        List<BookStatus> bookStatusList = bookStatusRepository.findAll();
        assertThat(bookStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBookStatus() throws Exception {
        int databaseSizeBeforeUpdate = bookStatusRepository.findAll().size();
        bookStatus.setId(longCount.incrementAndGet());

        // Create the BookStatus
        BookStatusDTO bookStatusDTO = bookStatusMapper.toDto(bookStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookStatus in the database
        List<BookStatus> bookStatusList = bookStatusRepository.findAll();
        assertThat(bookStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBookStatus() throws Exception {
        int databaseSizeBeforeUpdate = bookStatusRepository.findAll().size();
        bookStatus.setId(longCount.incrementAndGet());

        // Create the BookStatus
        BookStatusDTO bookStatusDTO = bookStatusMapper.toDto(bookStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bookStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookStatus in the database
        List<BookStatus> bookStatusList = bookStatusRepository.findAll();
        assertThat(bookStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBookStatus() throws Exception {
        // Initialize the database
        bookStatusRepository.saveAndFlush(bookStatus);

        int databaseSizeBeforeDelete = bookStatusRepository.findAll().size();

        // Delete the bookStatus
        restBookStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, bookStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookStatus> bookStatusList = bookStatusRepository.findAll();
        assertThat(bookStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
