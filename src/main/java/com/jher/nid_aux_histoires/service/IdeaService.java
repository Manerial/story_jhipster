package com.jher.nid_aux_histoires.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jher.nid_aux_histoires.service.dto.IdeaDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_LocationDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_ObjectDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_PersonaDTO;
import com.jher.nid_aux_histoires.service.dto.idea_generator.R_WritingOptionDTO;

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
	 * Génère une liste de personnages en fonction de contraintes
	 * 
	 * @param numberOfPersona : Nombre de mots à générer
	 * @param constraint      : Liste des contraintes à respecter
	 * @return la liste des personnages générés
	 */
	public List<R_PersonaDTO> generateR_Personas(int numberOfPersona, R_PersonaDTO constraint);

	/**
	 * Génère une liste de lieux en fonction de contraintes
	 * 
	 * @param numberOfLocations : Nombre de lieux à générer
	 * @param constraint        : Liste des contraintes à respecter
	 * @return la liste des lieux générés
	 */
	public List<R_LocationDTO> generateR_Locations(int numberOfLocations, R_LocationDTO constraint);

	/**
	 * Génère une liste d'options d'écriture en fonction de contraintes
	 * 
	 * @param numberOfWritingOption : Nombre d'options d'écriture à générer
	 * @param constraint            : Liste des contraintes à respecter
	 * @return la liste des d'options d'écriture générés
	 */
	public List<R_WritingOptionDTO> generateR_WritingOptions(int numberOfWritingOption, R_WritingOptionDTO constraint);

	/**
	 * Génère une liste d'objets en fonction de contraintes
	 * 
	 * @param numberOfObject : Nombre d'objets à générer
	 * @param constraint     : Liste des contraintes à respecter
	 * @return la liste des d'options d'écriture générés
	 */
	public List<R_ObjectDTO> generateR_Object(int numberOfObject, R_ObjectDTO constraint);
}
