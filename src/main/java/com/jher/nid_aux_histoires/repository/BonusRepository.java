package com.jher.nid_aux_histoires.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jher.nid_aux_histoires.domain.Bonus;

/**
 * Spring Data repository for the Bonus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
	@Query("select distinct bonus from Bonus bonus left join fetch bonus.book where bonus.book.id =:id")
	List<Bonus> findAllByBookId(@Param("id") Long id);
}
