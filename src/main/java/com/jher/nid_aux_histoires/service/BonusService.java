package com.jher.nid_aux_histoires.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jher.nid_aux_histoires.service.dto.BonusDTO;

/**
 * Service Interface for managing
 * {@link com.jher.nid_aux_histoires.domain.Bonus}.
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
	 * Get all the bonuses.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<BonusDTO> findAll(Pageable pageable);

	/**
	 * Get all the bonuses by Owner login.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<BonusDTO> findAllByOwnerLogin(Pageable pageable, String login);

	/**
	 * Get all the bonuses by book.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	List<BonusDTO> findAllByBookId(Long id);

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
