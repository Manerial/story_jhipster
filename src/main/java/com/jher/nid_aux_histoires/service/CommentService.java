package com.jher.nid_aux_histoires.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jher.nid_aux_histoires.service.dto.CommentDTO;

/**
 * Service Interface for managing
 * {@link com.jher.nid_aux_histoires.domain.Comment}.
 */
public interface CommentService {

	/**
	 * Save a comment.
	 *
	 * @param commentDTO the entity to save.
	 * @return the persisted entity.
	 */
	CommentDTO save(CommentDTO commentDTO);

	/**
	 * Get all the comments.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<CommentDTO> findAll(Pageable pageable);

	/**
	 * Get all the comments by bookId.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	List<CommentDTO> findAllByBookId(Long id);

	/**
	 * Get the "id" comment.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<CommentDTO> findOne(Long id);

	/**
	 * Delete the "id" comment.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
