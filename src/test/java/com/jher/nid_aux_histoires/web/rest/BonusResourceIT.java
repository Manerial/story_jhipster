package com.jher.nid_aux_histoires.web.rest;

import com.jher.nid_aux_histoires.NidAuxHistoiresApp;
import com.jher.nid_aux_histoires.domain.Bonus;
import com.jher.nid_aux_histoires.repository.BonusRepository;
import com.jher.nid_aux_histoires.service.BonusService;
import com.jher.nid_aux_histoires.service.dto.BonusDTO;
import com.jher.nid_aux_histoires.service.mapper.BonusMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BonusResource} REST controller.
 */
@SpringBootTest(classes = NidAuxHistoiresApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BonusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    @Autowired
    private BonusRepository bonusRepository;

    @Autowired
    private BonusMapper bonusMapper;

    @Autowired
    private BonusService bonusService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBonusMockMvc;

    private Bonus bonus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bonus createEntity(EntityManager em) {
        Bonus bonus = new Bonus()
            .name(DEFAULT_NAME)
            .extension(DEFAULT_EXTENSION);
        return bonus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bonus createUpdatedEntity(EntityManager em) {
        Bonus bonus = new Bonus()
            .name(UPDATED_NAME)
            .extension(UPDATED_EXTENSION);
        return bonus;
    }

    @BeforeEach
    public void initTest() {
        bonus = createEntity(em);
    }

    @Test
    @Transactional
    public void createBonus() throws Exception {
        int databaseSizeBeforeCreate = bonusRepository.findAll().size();
        // Create the Bonus
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);
        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isCreated());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeCreate + 1);
        Bonus testBonus = bonusList.get(bonusList.size() - 1);
        assertThat(testBonus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBonus.getExtension()).isEqualTo(DEFAULT_EXTENSION);
    }

    @Test
    @Transactional
    public void createBonusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bonusRepository.findAll().size();

        // Create the Bonus with an existing ID
        bonus.setId(1L);
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBonuses() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList
        restBonusMockMvc.perform(get("/api/bonuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION)));
    }
    
    @Test
    @Transactional
    public void getBonus() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get the bonus
        restBonusMockMvc.perform(get("/api/bonuses/{id}", bonus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bonus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION));
    }
    @Test
    @Transactional
    public void getNonExistingBonus() throws Exception {
        // Get the bonus
        restBonusMockMvc.perform(get("/api/bonuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBonus() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        int databaseSizeBeforeUpdate = bonusRepository.findAll().size();

        // Update the bonus
        Bonus updatedBonus = bonusRepository.findById(bonus.getId()).get();
        // Disconnect from session so that the updates on updatedBonus are not directly saved in db
        em.detach(updatedBonus);
        updatedBonus
            .name(UPDATED_NAME)
            .extension(UPDATED_EXTENSION);
        BonusDTO bonusDTO = bonusMapper.toDto(updatedBonus);

        restBonusMockMvc.perform(put("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isOk());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeUpdate);
        Bonus testBonus = bonusList.get(bonusList.size() - 1);
        assertThat(testBonus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBonus.getExtension()).isEqualTo(UPDATED_EXTENSION);
    }

    @Test
    @Transactional
    public void updateNonExistingBonus() throws Exception {
        int databaseSizeBeforeUpdate = bonusRepository.findAll().size();

        // Create the Bonus
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonusMockMvc.perform(put("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBonus() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        int databaseSizeBeforeDelete = bonusRepository.findAll().size();

        // Delete the bonus
        restBonusMockMvc.perform(delete("/api/bonuses/{id}", bonus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
