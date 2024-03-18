package com.jher.nid_aux_histoires.repository;

import com.jher.nid_aux_histoires.domain.Chapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Chapter entity.
 */
@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query(value = "select distinct chapter from Chapter chapter left join fetch chapter.images",
        countQuery = "select count(distinct chapter) from Chapter chapter")
    Page<Chapter> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct chapter from Chapter chapter left join fetch chapter.images")
    List<Chapter> findAllWithEagerRelationships();

    @Query("select chapter from Chapter chapter left join fetch chapter.images where chapter.id =:id")
    Optional<Chapter> findOneWithEagerRelationships(@Param("id") Long id);
}
