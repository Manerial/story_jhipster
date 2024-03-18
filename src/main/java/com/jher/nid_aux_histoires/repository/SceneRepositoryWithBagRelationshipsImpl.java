package com.jher.nid_aux_histoires.repository;

import com.jher.nid_aux_histoires.domain.Scene;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SceneRepositoryWithBagRelationshipsImpl implements SceneRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Scene> fetchBagRelationships(Optional<Scene> scene) {
        return scene.map(this::fetchImages);
    }

    @Override
    public Page<Scene> fetchBagRelationships(Page<Scene> scenes) {
        return new PageImpl<>(fetchBagRelationships(scenes.getContent()), scenes.getPageable(), scenes.getTotalElements());
    }

    @Override
    public List<Scene> fetchBagRelationships(List<Scene> scenes) {
        return Optional.of(scenes).map(this::fetchImages).orElse(Collections.emptyList());
    }

    Scene fetchImages(Scene result) {
        return entityManager
            .createQuery("select scene from Scene scene left join fetch scene.images where scene.id = :id", Scene.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Scene> fetchImages(List<Scene> scenes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, scenes.size()).forEach(index -> order.put(scenes.get(index).getId(), index));
        List<Scene> result = entityManager
            .createQuery("select scene from Scene scene left join fetch scene.images where scene in :scenes", Scene.class)
            .setParameter("scenes", scenes)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
