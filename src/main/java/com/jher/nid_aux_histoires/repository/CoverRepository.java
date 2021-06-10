package com.jher.nid_aux_histoires.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jher.nid_aux_histoires.domain.Cover;

/**
 * Spring Data repository for the Cover entity.
 */
@Repository
public interface CoverRepository extends JpaRepository<Cover, Long> {
	@Query(value = "SELECT cov.* FROM COVER cov WHERE cov_.owner_id = ?1", nativeQuery = true)
	List<Cover> findAllByAuthorId(Long id);
}
