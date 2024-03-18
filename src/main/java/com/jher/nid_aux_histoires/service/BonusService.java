package com.jher.nid_aux_histoires.service;

import com.jher.nid_aux_histoires.service.dto.BonusDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jher.nid_aux_histoires.domain.Bonus}.
 */
public interface BonusService {
    /**
     * Save a bonus.
     *
     * @param bonusDTO the entity to save.
     * @return the persisted entity.
     */
    BonusDTO save(BonusDTO bonusDTO);

    /**
     * Updates a bonus.
     *
     * @param bonusDTO the entity to update.
     * @return the persisted entity.
     */
    BonusDTO update(BonusDTO bonusDTO);

    /**
     * Partially updates a bonus.
     *
     * @param bonusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BonusDTO> partialUpdate(BonusDTO bonusDTO);

    /**
     * Get all the bonuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BonusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bonus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BonusDTO> findOne(Long id);

    /**
     * Delete the "id" bonus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
