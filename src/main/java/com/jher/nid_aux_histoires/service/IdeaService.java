package com.jher.nid_aux_histoires.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jher.nid_aux_histoires.service.dto.IdeaDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.Random_Interface;
import com.jher.nid_aux_histoires.service.tool.REG_Entity;

/**
 * Service Interface for managing
 * {@link com.jher.nid_aux_histoires.domain.Idea}.
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

	/**
	 * Génère une liste d'objets randoms en fonction de contraintes
	 * 
	 * @param number     : Nombre d'objets à générer
	 * @param type       : type d'objet à générer
	 * @param constraint : Liste des contraintes à respecter
	 * @return la liste des personnages générés
	 */
	List<Random_Interface> generate(int number, REG_Entity type, Random_Interface constraint);
}
