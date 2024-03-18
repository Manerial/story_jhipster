package com.jher.nid_aux_histoires.repository;

import com.jher.nid_aux_histoires.domain.Part;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PartRepositoryWithBagRelationships {
    Optional<Part> fetchBagRelationships(Optional<Part> part);

    List<Part> fetchBagRelationships(List<Part> parts);

    Page<Part> fetchBagRelationships(Page<Part> parts);
}
