package com.jher.nid_aux_histoires.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jher.nid_aux_histoires.IntegrationTest;
import com.jher.nid_aux_histoires.domain.Scene;
import com.jher.nid_aux_histoires.repository.SceneRepository;
import com.jher.nid_aux_histoires.service.SceneService;
import com.jher.nid_aux_histoires.service.dto.SceneDTO;
import com.jher.nid_aux_histoires.service.mapper.SceneMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SceneResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SceneResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TIMESTAMP_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIMESTAMP_START = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/scenes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SceneRepository sceneRepository;

    @Mock
    private SceneRepository sceneRepositoryMock;

    @Autowired
    private SceneMapper sceneMapper;

    @Mock
    private SceneService sceneServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSceneMockMvc;

    private Scene scene;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scene createEntity(EntityManager em) {
        Scene scene = new Scene().name(DEFAULT_NAME).number(DEFAULT_NUMBER).text(DEFAULT_TEXT).timestampStart(DEFAULT_TIMESTAMP_START);
        return scene;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scene createUpdatedEntity(EntityManager em) {
        Scene scene = new Scene().name(UPDATED_NAME).number(UPDATED_NUMBER).text(UPDATED_TEXT).timestampStart(UPDATED_TIMESTAMP_START);
        return scene;
    }

    @BeforeEach
    public void initTest() {
        scene = createEntity(em);
    }

    @Test
    @Transactional
    void createScene() throws Exception {
        int databaseSizeBeforeCreate = sceneRepository.findAll().size();
        // Create the Scene
        SceneDTO sceneDTO = sceneMapper.toDto(scene);
        restSceneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sceneDTO)))
            .andExpect(status().isCreated());

        // Validate the Scene in the database
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeCreate + 1);
        Scene testScene = sceneList.get(sceneList.size() - 1);
        assertThat(testScene.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testScene.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testScene.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testScene.getTimestampStart()).isEqualTo(DEFAULT_TIMESTAMP_START);
    }

    @Test
    @Transactional
    void createSceneWithExistingId() throws Exception {
        // Create the Scene with an existing ID
        scene.setId(1L);
        SceneDTO sceneDTO = sceneMapper.toDto(scene);

        int databaseSizeBeforeCreate = sceneRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSceneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sceneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scene in the database
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllScenes() throws Exception {
        // Initialize the database
        sceneRepository.saveAndFlush(scene);

        // Get all the sceneList
        restSceneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scene.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].timestampStart").value(hasItem(DEFAULT_TIMESTAMP_START.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllScenesWithEagerRelationshipsIsEnabled() throws Exception {
        when(sceneServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSceneMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(sceneServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllScenesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(sceneServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSceneMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(sceneRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getScene() throws Exception {
        // Initialize the database
        sceneRepository.saveAndFlush(scene);

        // Get the scene
        restSceneMockMvc
            .perform(get(ENTITY_API_URL_ID, scene.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scene.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.timestampStart").value(DEFAULT_TIMESTAMP_START.toString()));
    }

    @Test
    @Transactional
    void getNonExistingScene() throws Exception {
        // Get the scene
        restSceneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScene() throws Exception {
        // Initialize the database
        sceneRepository.saveAndFlush(scene);

        int databaseSizeBeforeUpdate = sceneRepository.findAll().size();

        // Update the scene
        Scene updatedScene = sceneRepository.findById(scene.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedScene are not directly saved in db
        em.detach(updatedScene);
        updatedScene.name(UPDATED_NAME).number(UPDATED_NUMBER).text(UPDATED_TEXT).timestampStart(UPDATED_TIMESTAMP_START);
        SceneDTO sceneDTO = sceneMapper.toDto(updatedScene);

        restSceneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sceneDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sceneDTO))
            )
            .andExpect(status().isOk());

        // Validate the Scene in the database
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeUpdate);
        Scene testScene = sceneList.get(sceneList.size() - 1);
        assertThat(testScene.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScene.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testScene.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testScene.getTimestampStart()).isEqualTo(UPDATED_TIMESTAMP_START);
    }

    @Test
    @Transactional
    void putNonExistingScene() throws Exception {
        int databaseSizeBeforeUpdate = sceneRepository.findAll().size();
        scene.setId(longCount.incrementAndGet());

        // Create the Scene
        SceneDTO sceneDTO = sceneMapper.toDto(scene);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSceneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sceneDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sceneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scene in the database
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScene() throws Exception {
        int databaseSizeBeforeUpdate = sceneRepository.findAll().size();
        scene.setId(longCount.incrementAndGet());

        // Create the Scene
        SceneDTO sceneDTO = sceneMapper.toDto(scene);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSceneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sceneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scene in the database
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScene() throws Exception {
        int databaseSizeBeforeUpdate = sceneRepository.findAll().size();
        scene.setId(longCount.incrementAndGet());

        // Create the Scene
        SceneDTO sceneDTO = sceneMapper.toDto(scene);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSceneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sceneDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Scene in the database
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSceneWithPatch() throws Exception {
        // Initialize the database
        sceneRepository.saveAndFlush(scene);

        int databaseSizeBeforeUpdate = sceneRepository.findAll().size();

        // Update the scene using partial update
        Scene partialUpdatedScene = new Scene();
        partialUpdatedScene.setId(scene.getId());

        partialUpdatedScene.number(UPDATED_NUMBER).text(UPDATED_TEXT).timestampStart(UPDATED_TIMESTAMP_START);

        restSceneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScene.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScene))
            )
            .andExpect(status().isOk());

        // Validate the Scene in the database
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeUpdate);
        Scene testScene = sceneList.get(sceneList.size() - 1);
        assertThat(testScene.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testScene.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testScene.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testScene.getTimestampStart()).isEqualTo(UPDATED_TIMESTAMP_START);
    }

    @Test
    @Transactional
    void fullUpdateSceneWithPatch() throws Exception {
        // Initialize the database
        sceneRepository.saveAndFlush(scene);

        int databaseSizeBeforeUpdate = sceneRepository.findAll().size();

        // Update the scene using partial update
        Scene partialUpdatedScene = new Scene();
        partialUpdatedScene.setId(scene.getId());

        partialUpdatedScene.name(UPDATED_NAME).number(UPDATED_NUMBER).text(UPDATED_TEXT).timestampStart(UPDATED_TIMESTAMP_START);

        restSceneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScene.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScene))
            )
            .andExpect(status().isOk());

        // Validate the Scene in the database
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeUpdate);
        Scene testScene = sceneList.get(sceneList.size() - 1);
        assertThat(testScene.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScene.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testScene.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testScene.getTimestampStart()).isEqualTo(UPDATED_TIMESTAMP_START);
    }

    @Test
    @Transactional
    void patchNonExistingScene() throws Exception {
        int databaseSizeBeforeUpdate = sceneRepository.findAll().size();
        scene.setId(longCount.incrementAndGet());

        // Create the Scene
        SceneDTO sceneDTO = sceneMapper.toDto(scene);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSceneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sceneDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sceneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scene in the database
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScene() throws Exception {
        int databaseSizeBeforeUpdate = sceneRepository.findAll().size();
        scene.setId(longCount.incrementAndGet());

        // Create the Scene
        SceneDTO sceneDTO = sceneMapper.toDto(scene);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSceneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sceneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Scene in the database
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScene() throws Exception {
        int databaseSizeBeforeUpdate = sceneRepository.findAll().size();
        scene.setId(longCount.incrementAndGet());

        // Create the Scene
        SceneDTO sceneDTO = sceneMapper.toDto(scene);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSceneMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sceneDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Scene in the database
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScene() throws Exception {
        // Initialize the database
        sceneRepository.saveAndFlush(scene);

        int databaseSizeBeforeDelete = sceneRepository.findAll().size();

        // Delete the scene
        restSceneMockMvc
            .perform(delete(ENTITY_API_URL_ID, scene.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
