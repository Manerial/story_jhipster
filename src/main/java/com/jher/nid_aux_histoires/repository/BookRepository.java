package com.jher.nid_aux_histoires.repository;

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
	@Query(value = "select distinct book from Book book", countQuery = "select count(distinct book) from Book book")
	Page<Book> findAll(Pageable pageable);

	@Query(value = "select distinct book from Book book where book.visibility = true", countQuery = "select count(distinct book) from Book book")
	Page<Book> findAllVisible(Pageable pageable);

	@Query("select book from Book book where book.author.login =:login")
	Page<Book> findAllByAuthorLogin(Pageable pageable, @Param("login") String login);

	@Query("select book from Book book where book.author.login =:login and book.visibility = true")
	Page<Book> findAllVisibleByAuthorLogin(Pageable pageable, @Param("login") String login);

	@Query(value = "SELECT book FROM Book book WHERE book.visibility = true AND book.id IN (SELECT bookStatus.book.id FROM BookStatus bookStatus WHERE bookStatus.user.login =:login AND bookStatus.favorit = true)")
	Page<Book> findAllFavoritsVisible(Pageable pageable, @Param("login") String login);

	@Query("select book from Book book where book.id =:id")
	Optional<Book> findOne(@Param("id") Long id);
}
