package com.jher.nid_aux_histoires.repository;

import com.jher.nid_aux_histoires.domain.BookStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BookStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookStatusRepository extends JpaRepository<BookStatus, Long> {
}
