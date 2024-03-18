package com.jher.nid_aux_histoires.service;

import com.jher.nid_aux_histoires.service.dto.PartDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jher.nid_aux_histoires.domain.Part}.
 */
public interface PartService {
    /**
     * Save a part.
     *
     * @param partDTO the entity to save.
     * @return the persisted entity.
     */
    PartDTO save(PartDTO partDTO);

    /**
     * Updates a part.
     *
     * @param partDTO the entity to update.
     * @return the persisted entity.
     */
    PartDTO update(PartDTO partDTO);

    /**
     * Partially updates a part.
     *
     * @param partDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PartDTO> partialUpdate(PartDTO partDTO);

    /**
     * Get all the parts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PartDTO> findAll(Pageable pageable);

    /**
     * Get all the parts with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PartDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" part.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartDTO> findOne(Long id);

    /**
     * Delete the "id" part.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
