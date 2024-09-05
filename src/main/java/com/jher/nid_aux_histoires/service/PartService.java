package com.jher.nid_aux_histoires.service;

import java.util.List;
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
	 * Get all the parts with eager load of many-to-many relationships.
	 *
	 * @return the list of entities.
	 */
	Page<PartDTO> findAllWithEagerRelationships(Pageable pageable);

	/**
	 * Get all the parts with eager load of many-to-many relationships.
	 *
	 * @return the list of entities.
	 */
	List<PartDTO> findAllWithEagerRelationships();

	/**
	 * Get all the parts related to a book.
	 *
	 * @return the list of entities.
	 */
	List<PartDTO> findAllByBookId(Long bookId);

	/**
	 * Get the "id" part.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<PartDTO> findOne(Long id);

	/**
	 * Delete the "id" part.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
