package com.jher.nid_aux_histoires.repository;

import com.jher.nid_aux_histoires.domain.Bonus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bonus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
}
