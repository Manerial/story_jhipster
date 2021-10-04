package com.jher.nid_aux_histoires.service;

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
	 * Get all the covers related to an Owner.
	 *
	 * @param pageable the pagination information.
	 * @param id       the id of the book.
	 * @return the list of entities.
	 */
	Page<CoverDTO> findAllByOwnerId(Pageable pageable, Long id);

	/**
	 * Get all the covers by Owner login.
	 *
	 * @param pageable the pagination information.
	 * @param login    the author login.
	 * @return the list of entities.
	 */
	Page<CoverDTO> findAllByOwnerLogin(Pageable pageable, String login);

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
}
