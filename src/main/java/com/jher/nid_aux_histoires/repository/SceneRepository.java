package com.jher.nid_aux_histoires.repository;

import com.jher.nid_aux_histoires.domain.Scene;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Scene entity.
 */
@Repository
public interface SceneRepository extends JpaRepository<Scene, Long> {

    @Query(value = "select distinct scene from Scene scene left join fetch scene.images",
        countQuery = "select count(distinct scene) from Scene scene")
    Page<Scene> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct scene from Scene scene left join fetch scene.images")
    List<Scene> findAllWithEagerRelationships();

    @Query("select scene from Scene scene left join fetch scene.images where scene.id =:id")
    Optional<Scene> findOneWithEagerRelationships(@Param("id") Long id);
}
