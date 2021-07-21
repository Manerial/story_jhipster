package com.jher.nid_aux_histoires.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jher.nid_aux_histoires.domain.BookStatus;

/**
 * Spring Data repository for the BookStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookStatusRepository extends JpaRepository<BookStatus, Long> {

	@Query("select bookStatus from BookStatus bookStatus where bookStatus.user.login =:login")
	Page<BookStatus> findAllByUserLogin(Pageable pageable, @Param("login") String login);

	@Query("select bookStatus from BookStatus bookStatus where bookStatus.user.login =:login and bookStatus.book.id =:bookId")
	Optional<BookStatus> findOneByUserAndBook(@Param("login") String login, @Param("bookId") Long bookId);
}
