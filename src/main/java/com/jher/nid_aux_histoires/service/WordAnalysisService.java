package com.jher.nid_aux_histoires.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jher.nid_aux_histoires.service.dto.WordAnalysisDTO;
import com.jher.nid_aux_histoires.service.tool.REG_WordAnalysis;

/**
 * Service Interface for managing
 * {@link com.jher.nid_aux_histoires.domain.WordAnalysis}.
 */
public interface WordAnalysisService {

	/**
	 * Save a wordAnalysis.
	 *
	 * @param wordAnalysisDTO the entity to save.
	 * @return the persisted entity.
	 */
	WordAnalysisDTO save(WordAnalysisDTO wordAnalysisDTO);

	/**
	 * Get all the wordAnalyses.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<WordAnalysisDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" wordAnalysis.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WordAnalysisDTO> findOne(Long id);

	/**
	 * Delete the "id" wordAnalysis.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Récupère la liste des types de mots générables disponibles
	 * 
	 * @return la liste des types de mots générables disponibles
	 */
	public List<Map<String, String>> getListOfTypes();

	/**
	 * Génère une liste de mots en fonction d'un type
	 * 
	 * @param numberOfWords : Nombre de mots à générer
	 * @param fixLength     : La taille des mots à générer. Si inférieur à 2, random
	 * @param type          : type de mots à générer
	 * @return la liste des mots générés
	 */
	public List<String> generate(int numberOfWords, int fixLength, REG_WordAnalysis type);

	/**
	 * Génère un mot en fonction d'un type
	 * 
	 * @param type : type de mot à générer
	 * @return le mot généré
	 */
	public String generate(REG_WordAnalysis type);
}
