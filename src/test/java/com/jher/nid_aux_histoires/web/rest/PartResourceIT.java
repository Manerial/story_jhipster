package com.jher.nid_aux_histoires.web.rest;

import com.jher.nid_aux_histoires.NidAuxHistoiresApp;
import com.jher.nid_aux_histoires.domain.Part;
import com.jher.nid_aux_histoires.repository.PartRepository;
import com.jher.nid_aux_histoires.service.PartService;
import com.jher.nid_aux_histoires.service.dto.PartDTO;
import com.jher.nid_aux_histoires.service.mapper.PartMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PartResource} REST controller.
 */
@SpringBootTest(classes = NidAuxHistoiresApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    @Autowired
    private PartRepository partRepository;

    @Mock
    private PartRepository partRepositoryMock;

    @Autowired
    private PartMapper partMapper;

    @Mock
    private PartService partServiceMock;

    @Autowired
    private PartService partService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartMockMvc;

    private Part part;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Part createEntity(EntityManager em) {
        Part part = new Part()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .number(DEFAULT_NUMBER);
        return part;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Part createUpdatedEntity(EntityManager em) {
        Part part = new Part()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .number(UPDATED_NUMBER);
        return part;
    }

    @BeforeEach
    public void initTest() {
        part = createEntity(em);
    }

    @Test
    @Transactional
    public void createPart() throws Exception {
        int databaseSizeBeforeCreate = partRepository.findAll().size();
        // Create the Part
        PartDTO partDTO = partMapper.toDto(part);
        restPartMockMvc.perform(post("/api/parts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partDTO)))
            .andExpect(status().isCreated());

        // Validate the Part in the database
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeCreate + 1);
        Part testPart = partList.get(partList.size() - 1);
        assertThat(testPart.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPart.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPart.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createPartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partRepository.findAll().size();

        // Create the Part with an existing ID
        part.setId(1L);
        PartDTO partDTO = partMapper.toDto(part);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartMockMvc.perform(post("/api/parts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Part in the database
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllParts() throws Exception {
        // Initialize the database
        partRepository.saveAndFlush(part);

        // Get all the partList
        restPartMockMvc.perform(get("/api/parts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(part.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPartsWithEagerRelationshipsIsEnabled() throws Exception {
        when(partServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPartMockMvc.perform(get("/api/parts?eagerload=true"))
            .andExpect(status().isOk());

        verify(partServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPartsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(partServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPartMockMvc.perform(get("/api/parts?eagerload=true"))
            .andExpect(status().isOk());

        verify(partServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPart() throws Exception {
        // Initialize the database
        partRepository.saveAndFlush(part);

        // Get the part
        restPartMockMvc.perform(get("/api/parts/{id}", part.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(part.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingPart() throws Exception {
        // Get the part
        restPartMockMvc.perform(get("/api/parts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePart() throws Exception {
        // Initialize the database
        partRepository.saveAndFlush(part);

        int databaseSizeBeforeUpdate = partRepository.findAll().size();

        // Update the part
        Part updatedPart = partRepository.findById(part.getId()).get();
        // Disconnect from session so that the updates on updatedPart are not directly saved in db
        em.detach(updatedPart);
        updatedPart
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .number(UPDATED_NUMBER);
        PartDTO partDTO = partMapper.toDto(updatedPart);

        restPartMockMvc.perform(put("/api/parts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partDTO)))
            .andExpect(status().isOk());

        // Validate the Part in the database
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeUpdate);
        Part testPart = partList.get(partList.size() - 1);
        assertThat(testPart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPart.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPart.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingPart() throws Exception {
        int databaseSizeBeforeUpdate = partRepository.findAll().size();

        // Create the Part
        PartDTO partDTO = partMapper.toDto(part);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartMockMvc.perform(put("/api/parts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Part in the database
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePart() throws Exception {
        // Initialize the database
        partRepository.saveAndFlush(part);

        int databaseSizeBeforeDelete = partRepository.findAll().size();

        // Delete the part
        restPartMockMvc.perform(delete("/api/parts/{id}", part.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Part> partList = partRepository.findAll();
        assertThat(partList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
