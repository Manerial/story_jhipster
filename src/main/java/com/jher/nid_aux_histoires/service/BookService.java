package com.jher.nid_aux_histoires.service;

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
	 * Switch visibility of book.
	 *
	 * @param id the id of the entity.
	 */
	BookDTO changeVisibility(Long bookId);

	/**
	 * Get all the books.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<BookDTO> findAll(Pageable pageable);

	/**
	 * Get all the books tagged as Visible.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<BookDTO> findAllVisible(Pageable pageable);

	/**
	 * Get all the books by Author.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<BookDTO> findAllByAuthorId(Pageable pageable, String login);

	/**
	 * Get all the books tagged as Visible by Author.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<BookDTO> findAllVisibleByAuthorId(Pageable pageable, String login);

	/**
	 * Get the "id" book.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<BookDTO> findOne(Long id);

	/**
	 * Get the "id" book light version.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<BookDTO> findOneLight(Long id);

	/**
	 * Delete the "id" book.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
