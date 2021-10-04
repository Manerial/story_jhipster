package com.jher.nid_aux_histoires.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jher.nid_aux_histoires.domain.Comment;

/**
 * Spring Data repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("select distinct comment from Comment comment left join fetch comment.book where comment.book.id =:id")
	List<Comment> findAllByBookId(@Param("id") Long id);
}
