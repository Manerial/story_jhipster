package com.jher.nid_aux_histoires.repository;

import com.jher.nid_aux_histoires.domain.Scene;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SceneRepositoryWithBagRelationships {
    Optional<Scene> fetchBagRelationships(Optional<Scene> scene);

    List<Scene> fetchBagRelationships(List<Scene> scenes);

    Page<Scene> fetchBagRelationships(Page<Scene> scenes);
}
