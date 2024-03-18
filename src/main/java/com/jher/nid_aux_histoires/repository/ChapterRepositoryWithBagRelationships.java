package com.jher.nid_aux_histoires.repository;

import com.jher.nid_aux_histoires.domain.Chapter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ChapterRepositoryWithBagRelationships {
    Optional<Chapter> fetchBagRelationships(Optional<Chapter> chapter);

    List<Chapter> fetchBagRelationships(List<Chapter> chapters);

    Page<Chapter> fetchBagRelationships(Page<Chapter> chapters);
}
