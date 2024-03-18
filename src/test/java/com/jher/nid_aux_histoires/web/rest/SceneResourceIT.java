package com.jher.nid_aux_histoires.web.rest;

import com.jher.nid_aux_histoires.NidAuxHistoiresApp;
import com.jher.nid_aux_histoires.domain.Scene;
import com.jher.nid_aux_histoires.repository.SceneRepository;
import com.jher.nid_aux_histoires.service.SceneService;
import com.jher.nid_aux_histoires.service.dto.SceneDTO;
import com.jher.nid_aux_histoires.service.mapper.SceneMapper;

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
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SceneResource} REST controller.
 */
@SpringBootTest(classes = NidAuxHistoiresApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SceneResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TIMESTAMP_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIMESTAMP_START = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SceneRepository sceneRepository;

    @Mock
    private SceneRepository sceneRepositoryMock;

    @Autowired
    private SceneMapper sceneMapper;

    @Mock
    private SceneService sceneServiceMock;

    @Autowired
    private SceneService sceneService;

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
        Scene scene = new Scene()
            .name(DEFAULT_NAME)
            .number(DEFAULT_NUMBER)
            .text(DEFAULT_TEXT)
            .timestampStart(DEFAULT_TIMESTAMP_START);
        return scene;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scene createUpdatedEntity(EntityManager em) {
        Scene scene = new Scene()
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .text(UPDATED_TEXT)
            .timestampStart(UPDATED_TIMESTAMP_START);
        return scene;
    }

    @BeforeEach
    public void initTest() {
        scene = createEntity(em);
    }

    @Test
    @Transactional
    public void createScene() throws Exception {
        int databaseSizeBeforeCreate = sceneRepository.findAll().size();
        // Create the Scene
        SceneDTO sceneDTO = sceneMapper.toDto(scene);
        restSceneMockMvc.perform(post("/api/scenes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sceneDTO)))
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
    public void createSceneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sceneRepository.findAll().size();

        // Create the Scene with an existing ID
        scene.setId(1L);
        SceneDTO sceneDTO = sceneMapper.toDto(scene);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSceneMockMvc.perform(post("/api/scenes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sceneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scene in the database
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllScenes() throws Exception {
        // Initialize the database
        sceneRepository.saveAndFlush(scene);

        // Get all the sceneList
        restSceneMockMvc.perform(get("/api/scenes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scene.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].timestampStart").value(hasItem(DEFAULT_TIMESTAMP_START.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllScenesWithEagerRelationshipsIsEnabled() throws Exception {
        when(sceneServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSceneMockMvc.perform(get("/api/scenes?eagerload=true"))
            .andExpect(status().isOk());

        verify(sceneServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllScenesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(sceneServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSceneMockMvc.perform(get("/api/scenes?eagerload=true"))
            .andExpect(status().isOk());

        verify(sceneServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getScene() throws Exception {
        // Initialize the database
        sceneRepository.saveAndFlush(scene);

        // Get the scene
        restSceneMockMvc.perform(get("/api/scenes/{id}", scene.getId()))
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
    public void getNonExistingScene() throws Exception {
        // Get the scene
        restSceneMockMvc.perform(get("/api/scenes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScene() throws Exception {
        // Initialize the database
        sceneRepository.saveAndFlush(scene);

        int databaseSizeBeforeUpdate = sceneRepository.findAll().size();

        // Update the scene
        Scene updatedScene = sceneRepository.findById(scene.getId()).get();
        // Disconnect from session so that the updates on updatedScene are not directly saved in db
        em.detach(updatedScene);
        updatedScene
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .text(UPDATED_TEXT)
            .timestampStart(UPDATED_TIMESTAMP_START);
        SceneDTO sceneDTO = sceneMapper.toDto(updatedScene);

        restSceneMockMvc.perform(put("/api/scenes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sceneDTO)))
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
    public void updateNonExistingScene() throws Exception {
        int databaseSizeBeforeUpdate = sceneRepository.findAll().size();

        // Create the Scene
        SceneDTO sceneDTO = sceneMapper.toDto(scene);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSceneMockMvc.perform(put("/api/scenes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sceneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scene in the database
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScene() throws Exception {
        // Initialize the database
        sceneRepository.saveAndFlush(scene);

        int databaseSizeBeforeDelete = sceneRepository.findAll().size();

        // Delete the scene
        restSceneMockMvc.perform(delete("/api/scenes/{id}", scene.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Scene> sceneList = sceneRepository.findAll();
        assertThat(sceneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
