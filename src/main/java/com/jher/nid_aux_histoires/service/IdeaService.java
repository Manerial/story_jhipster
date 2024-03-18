package com.jher.nid_aux_histoires.service;

import com.jher.nid_aux_histoires.service.dto.IdeaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jher.nid_aux_histoires.domain.Idea}.
 */
public interface IdeaService {
    /**
     * Save a idea.
     *
     * @param ideaDTO the entity to save.
     * @return the persisted entity.
     */
    IdeaDTO save(IdeaDTO ideaDTO);

    /**
     * Updates a idea.
     *
     * @param ideaDTO the entity to update.
     * @return the persisted entity.
     */
    IdeaDTO update(IdeaDTO ideaDTO);

    /**
     * Partially updates a idea.
     *
     * @param ideaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IdeaDTO> partialUpdate(IdeaDTO ideaDTO);

    /**
     * Get all the ideas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IdeaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" idea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IdeaDTO> findOne(Long id);

    /**
     * Delete the "id" idea.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
