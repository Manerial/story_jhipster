package com.jher.nid_aux_histoires.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jher.nid_aux_histoires.service.dto.SceneDTO;

/**
 * Service Interface for managing
 * {@link com.jher.nid_aux_histoires.domain.Scene}.
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

	Page<SceneDTO> findAllByAuthorLogin(Pageable pageable, String authorLogin);

	/**
	 * Get all the scenes by author Id.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	String findAuthorLoginBySceneId(Long id);

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
