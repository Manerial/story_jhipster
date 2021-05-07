package com.jher.nid_aux_histoires.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jher.nid_aux_histoires.service.dto.BookDTO;

/**
 * Service Interface for managing
 * {@link com.jher.nid_aux_histoires.domain.Book}.
 */
public interface BookService {

	/**
	 * Save a book.
	 *
	 * @param bookDTO the entity to save.
	 * @return the persisted entity.
	 */
	BookDTO save(BookDTO bookDTO);

	/**
	 * Save a book and all its subentities.
	 *
	 * @param bookDTO the entity to save.
	 * @return the persisted entity.
	 */
	BookDTO saveBash(BookDTO bookDTO);

	/**
	 * Get all the books.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<BookDTO> findAll(Pageable pageable);

	/**
	 * Get all the books with eager load of many-to-many relationships.
	 *
	 * @return the list of entities.
	 */
	Page<BookDTO> findAllWithEagerRelationships(Pageable pageable);

	/**
	 * Get all the books with eager load of many-to-many relationships.
	 *
	 * @return the list of entities.
	 */
	List<BookDTO> findAllWithEagerRelationships();

	/**
	 * Get the "id" book.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<BookDTO> findOne(Long id);

	/**
	 * Delete the "id" book.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
