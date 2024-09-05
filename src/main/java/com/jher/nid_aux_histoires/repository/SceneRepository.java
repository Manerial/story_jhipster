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
	Page<Scene> findAll(Pageable pageable);

	@Query("select distinct scene from Scene scene where scene.chapter.part.book.author.login =:login")
	Page<Scene> findAllByAuthorLogin(Pageable pageable, @Param("login") String login);

	@Query("select distinct scene from Scene scene where scene.chapter.id =:chapterId")
	List<Scene> findAllByChapterId(@Param("chapterId") Long chapterId);

	@Query("select scene from Scene scene where scene.id =:id")
	Optional<Scene> findOne(@Param("id") Long id);

	@Query("select scene.chapter.part.book.author.login from Scene scene where scene.id =:id")
	String findAuthorLoginBySceneId(@Param("id") Long id);
}
