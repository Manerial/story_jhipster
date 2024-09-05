package com.jher.nid_aux_histoires.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jher.nid_aux_histoires.service.dto.PartDTO;

/**
 * Service Interface for managing
 * {@link com.jher.nid_aux_histoires.domain.Part}.
 */
public interface PartService {

	/**
	 * Save a part.
	 *
	 * @param partDTO the entity to save.
	 * @return the persisted entity.
	 */
	PartDTO save(PartDTO partDTO);

	/**
	 * Save a part and all its subentities.
	 *
	 * @param partDTO the entity to save.
	 * @return the persisted entity.
	 */
	PartDTO saveBash(PartDTO partDTO);

	/**
	 * Get all the parts.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<PartDTO> findAll(Pageable pageable);

	/**
	 * Get all the parts related to an author.
	 *
	 * @param pageable the pagination information.
	 * @param login    the author login.
	 * @return the list of entities.
	 */
	Page<PartDTO> findAllByAuthorLogin(Pageable pageable, String login);

	/**
	 * Get the "id" part.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<PartDTO> findOne(Long id);

	/**
	 * Get the author of the "id" part.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	String findAuthorLoginByPartId(Long id);

	/**
	 * Delete the "id" part.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
