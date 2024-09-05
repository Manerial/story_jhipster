package com.jher.nid_aux_histoires.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jher.nid_aux_histoires.service.dto.BookStatusDTO;

/**
 * Service Interface for managing
 * {@link com.jher.nid_aux_histoires.domain.BookStatus}.
 */
public interface BookStatusService {

	/**
	 * Save a bookStatus.
	 *
	 * @param bookStatusDTO the entity to save.
	 * @return the persisted entity.
	 */
	BookStatusDTO save(BookStatusDTO bookStatusDTO);

	/**
	 * Get all the bookStatuses.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<BookStatusDTO> findAll(Pageable pageable);

	/**
	 * Get all the bookStatuses by logged user.
	 *
	 * @param pageable the pagination information.
	 * @param login    the login of the user.
	 * @return the list of entities.
	 */
	Page<BookStatusDTO> findAllByUser(Pageable pageable, String login);

	/**
	 * Get the "id" bookStatus.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<BookStatusDTO> findOne(Long id);

	/**
	 * Delete the "id" bookStatus.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
