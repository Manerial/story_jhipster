package com.jher.nid_aux_histoires.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jher.nid_aux_histoires.domain.Cover;

/**
 * Spring Data repository for the Cover entity.
 */
@Repository
public interface CoverRepository extends JpaRepository<Cover, Long> {
	@Query("select distinct cover from Cover cover where cover.owner.id =:id")
	Page<Cover> findAllByOwnerId(Pageable pageable, @Param("id") Long id);

	@Query("select distinct bonus from Bonus bonus where bonus.owner.login =:login")
	Page<Cover> findAllByOwnerLogin(Pageable pageable, @Param("login") String login);
}
