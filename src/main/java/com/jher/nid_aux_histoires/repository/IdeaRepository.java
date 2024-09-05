package com.jher.nid_aux_histoires.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jher.nid_aux_histoires.domain.Idea;

/**
 * Spring Data repository for the Idea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

	List<Idea> findByType(String type);

	Idea findByTypeAndComplement(String string, String complement);
}
