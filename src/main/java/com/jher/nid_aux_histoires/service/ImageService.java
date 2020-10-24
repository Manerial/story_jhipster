package com.jher.nid_aux_histoires.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jher.nid_aux_histoires.service.dto.ImageDTO;

/**
 * Service Interface for managing
 * {@link com.jher.nid_aux_histoires.domain.Image}.
 */
public interface ImageService {

	/**
	 * Save a image.
	 *
	 * @param imageDTO the entity to save.
	 * @return the persisted entity.
	 */
	ImageDTO save(ImageDTO imageDTO);

	/**
	 * Get all the images.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<ImageDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" image.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<ImageDTO> findOne(Long id);

	/**
	 * Delete the "id" image.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Get all the images related to a Book.
	 *
	 * @param id the id of the book.
	 * @return the list of entities.
	 */
	List<ImageDTO> findAllByBookId(Long id);

	/**
	 * Get all the images related to a Part.
	 *
	 * @param id the id of the Part.
	 * @return the list of entities.
	 */
	List<ImageDTO> findAllByPartId(Long id);

	/**
	 * Get all the images related to a Chapter.
	 *
	 * @param id the id of the Chapter.
	 * @return the list of entities.
	 */
	List<ImageDTO> findAllByChapterId(Long id);

	/**
	 * Get all the images related to a Scene.
	 *
	 * @param id the id of the Scene.
	 * @return the list of entities.
	 */
	List<ImageDTO> findAllBySceneId(Long id);
}
