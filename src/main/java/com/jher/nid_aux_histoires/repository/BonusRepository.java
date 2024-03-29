package com.jher.nid_aux_histoires.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jher.nid_aux_histoires.domain.Bonus;

/**
 * Spring Data repository for the Bonus entity.
 */
@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
	@Query("select distinct bonus from Bonus bonus where bonus.book.id =:bookId")
	List<Bonus> findAllByBookId(@Param("bookId") Long bookId);

	@Query("select distinct bonus from Bonus bonus where bonus.owner.login =:login")
	Page<Bonus> findAllByOwnerLogin(Pageable pageable, @Param("login") String login);
}
