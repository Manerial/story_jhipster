package com.jher.nid_aux_histoires.service;

import com.jher.nid_aux_histoires.service.dto.ChapterDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.jher.nid_aux_histoires.domain.Chapter}.
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
     * Get all the chapters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChapterDTO> findAll(Pageable pageable);

    /**
     * Get all the chapters with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ChapterDTO> findAllWithEagerRelationships(Pageable pageable);


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
