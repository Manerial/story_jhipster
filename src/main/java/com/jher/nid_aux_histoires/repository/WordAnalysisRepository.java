package com.jher.nid_aux_histoires.repository;

import com.jher.nid_aux_histoires.domain.WordAnalysis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WordAnalysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WordAnalysisRepository extends JpaRepository<WordAnalysis, Long> {
}
