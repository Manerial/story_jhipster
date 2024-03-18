package com.jher.nid_aux_histoires.service;

import com.jher.nid_aux_histoires.service.dto.WordAnalysisDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.jher.nid_aux_histoires.domain.WordAnalysis}.
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
}
