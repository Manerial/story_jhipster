package com.jher.nid_aux_histoires.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jher.nid_aux_histoires.domain.Book;

/**
 * Spring Data repository for the Book entity.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	@Query(value = "select distinct book from Book book left join fetch book.images", countQuery = "select count(distinct book) from Book book")
	Page<Book> findAllWithEagerRelationships(Pageable pageable);

	@Query("select distinct book from Book book left join fetch book.images")
	List<Book> findAllWithEagerRelationships();

	@Query("select book from Book book left join fetch book.images where book.id =:id")
	Optional<Book> findOneWithEagerRelationships(@Param("id") Long id);

	@Query("select book from Book book left join fetch book.images where book.author.login =:login")
	List<Book> findAllByAuthorLogin(@Param("login") String login);
}
