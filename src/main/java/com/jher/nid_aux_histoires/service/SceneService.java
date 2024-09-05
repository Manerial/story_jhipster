package com.jher.nid_aux_histoires.service;

import com.jher.nid_aux_histoires.service.dto.SceneDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.jher.nid_aux_histoires.domain.Scene}.
 */
public interface SceneService {

    /**
     * Save a scene.
     *
     * @param sceneDTO the entity to save.
     * @return the persisted entity.
     */
    SceneDTO save(SceneDTO sceneDTO);

    /**
     * Get all the scenes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SceneDTO> findAll(Pageable pageable);

    /**
     * Get all the scenes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<SceneDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" scene.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SceneDTO> findOne(Long id);

    /**
     * Delete the "id" scene.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
