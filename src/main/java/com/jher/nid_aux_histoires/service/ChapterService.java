package com.jher.nid_aux_histoires.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jher.nid_aux_histoires.service.dto.ChapterDTO;

/**
 * Service Interface for managing
 * {@link com.jher.nid_aux_histoires.domain.Chapter}.
 */
public interface ChapterService {

	/**
	 * Save a chapter.
	 *
	 * @param chapterDTO the entity to save.
	 * @return the persisted entity.
	 */
	ChapterDTO save(ChapterDTO chapterDTO);

	/**
	 * Save a chapter and all its subentities.
	 *
	 * @param chapterDTO the entity to save.
	 * @return the persisted entity.
	 */
	ChapterDTO saveBash(ChapterDTO chapterDTO);

	/**
	 * Get all the chapters.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<ChapterDTO> findAll(Pageable pageable);

	/**
	 * Get all the chapters by author Id.
	 *
	 * @param pageable the pagination information.
	 * @param login    the author login.
	 * @return the list of entities.
	 */
	Page<ChapterDTO> findAllByAuthorLogin(Pageable pageable, String login);

	/**
	 * Get the author of the "id" chapter.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	String findAuthorLoginByChapterId(Long id);

	/**
	 * Get the "id" chapter.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<ChapterDTO> findOne(Long id);

	/**
	 * Delete the "id" chapter.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
