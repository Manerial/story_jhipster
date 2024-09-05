package com.jher.nid_aux_histoires.repository;

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
}
