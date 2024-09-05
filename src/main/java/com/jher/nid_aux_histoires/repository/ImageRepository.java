package com.jher.nid_aux_histoires.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jher.nid_aux_histoires.domain.Image;

/**
 * Spring Data repository for the Image entity.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	@Query(value = "SELECT img.* FROM IMAGE img JOIN BOOK_IMAGE img_ ON img.id = img_.image_id WHERE img_.book_id = ?1", nativeQuery = true)
	List<Image> findAllByBookId(Long id);

	@Query(value = "SELECT img.* FROM IMAGE img JOIN PART_IMAGE img_ ON img.id = img_.image_id WHERE img_.part_id = ?1", nativeQuery = true)
	List<Image> findAllByPartId(Long id);

	@Query(value = "SELECT img.* FROM IMAGE img JOIN CHAPTER_IMAGE img_ ON img.id = img_.image_id WHERE img_.chapter_id = ?1", nativeQuery = true)
	List<Image> findAllByChapterId(Long id);

	@Query(value = "SELECT img.* FROM IMAGE img JOIN SCENE_IMAGE img_ ON img.id = img_.image_id WHERE img_.scene_id = ?1", nativeQuery = true)
	List<Image> findAllBySceneId(Long id);
}
