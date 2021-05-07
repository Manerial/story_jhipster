package com.jher.nid_aux_histoires.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jher.nid_aux_histoires.domain.Part;

/**
 * Spring Data repository for the Part entity.
 */
@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

	@Query(value = "select distinct part from Part part left join fetch part.images", countQuery = "select count(distinct part) from Part part")
	Page<Part> findAllWithEagerRelationships(Pageable pageable);

	@Query("select distinct part from Part part left join fetch part.images")
	List<Part> findAllWithEagerRelationships();

	@Query("select part from Part part left join fetch part.images where part.id =:id")
	Optional<Part> findOneWithEagerRelationships(@Param("id") Long id);

	@Query("select part from Part part left join fetch part.images where part.book.id =:bookId")
	List<Part> findAllByBookId(@Param("bookId") Long bookId);
}
