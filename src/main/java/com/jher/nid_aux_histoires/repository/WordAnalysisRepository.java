package com.jher.nid_aux_histoires.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jher.nid_aux_histoires.domain.WordAnalysis;

/**
 * Spring Data repository for the WordAnalysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WordAnalysisRepository extends JpaRepository<WordAnalysis, Long> {

	WordAnalysis findByType(String type);
}
