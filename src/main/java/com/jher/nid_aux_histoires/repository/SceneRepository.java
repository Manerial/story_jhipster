package com.jher.nid_aux_histoires.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jher.nid_aux_histoires.domain.Scene;

/**
 * Spring Data repository for the Scene entity.
 */
@Repository
public interface SceneRepository extends JpaRepository<Scene, Long> {

	@Query(value = "select distinct scene from Scene scene", countQuery = "select count(distinct scene) from Scene scene")
	Page<Scene> findAllWithEagerRelationships(Pageable pageable);

	@Query("select distinct scene from Scene scene")
	List<Scene> findAllWithEagerRelationships();

	@Query("select scene from Scene scene where scene.id =:id")
	Optional<Scene> findOneWithEagerRelationships(@Param("id") Long id);
}
