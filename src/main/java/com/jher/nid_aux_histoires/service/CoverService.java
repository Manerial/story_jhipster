package com.jher.nid_aux_histoires.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jher.nid_aux_histoires.service.dto.CoverDTO;

/**
 * Service Interface for managing
 * {@link com.jher.nid_aux_histoires.domain.Cover}.
 */
public interface CoverService {

	/**
	 * Save a cover.
	 *
	 * @param coverDTO the entity to save.
	 * @return the persisted entity.
	 */
	CoverDTO save(CoverDTO coverDTO);

	/**
	 * Get all the covers.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<CoverDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" cover.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<CoverDTO> findOne(Long id);

	/**
	 * Delete the "id" cover.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Get all the covers related to an Author.
	 *
	 * @param id the id of the book.
	 * @return the list of entities.
	 */

	List<CoverDTO> findAllByAuthorId(Long id);
}
