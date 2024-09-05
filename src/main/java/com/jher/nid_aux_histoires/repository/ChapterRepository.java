package com.jher.nid_aux_histoires.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jher.nid_aux_histoires.domain.Chapter;

/**
 * Spring Data repository for the Chapter entity.
 */
@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
	@Query(value = "select distinct chapter from Chapter chapter", countQuery = "select count(distinct chapter) from Chapter chapter")
	Page<Chapter> findAll(Pageable pageable);

	@Query("select distinct chapter from Chapter chapter where chapter.part.book.author.login =:login")
	Page<Chapter> findAllByAuthorLogin(Pageable pageable, @Param("login") String login);

	@Query("select distinct chapter from Chapter chapter where chapter.part.id =:partId")
	List<Chapter> findAllByPartId(@Param("partId") Long partId);

	@Query("select chapter from Chapter chapter where chapter.id =:id")
	Optional<Chapter> findOne(@Param("id") Long id);

	@Query("select chapter.part.book.author.login from Chapter chapter where chapter.id =:id")
	String findAuthorLoginByChapterId(@Param("id") Long id);
}
