package com.jher.nid_aux_histoires.repository;

import com.jher.nid_aux_histoires.domain.Chapter;
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
public class ChapterRepositoryWithBagRelationshipsImpl implements ChapterRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Chapter> fetchBagRelationships(Optional<Chapter> chapter) {
        return chapter.map(this::fetchImages);
    }

    @Override
    public Page<Chapter> fetchBagRelationships(Page<Chapter> chapters) {
        return new PageImpl<>(fetchBagRelationships(chapters.getContent()), chapters.getPageable(), chapters.getTotalElements());
    }

    @Override
    public List<Chapter> fetchBagRelationships(List<Chapter> chapters) {
        return Optional.of(chapters).map(this::fetchImages).orElse(Collections.emptyList());
    }

    Chapter fetchImages(Chapter result) {
        return entityManager
            .createQuery("select chapter from Chapter chapter left join fetch chapter.images where chapter.id = :id", Chapter.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Chapter> fetchImages(List<Chapter> chapters) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, chapters.size()).forEach(index -> order.put(chapters.get(index).getId(), index));
        List<Chapter> result = entityManager
            .createQuery("select chapter from Chapter chapter left join fetch chapter.images where chapter in :chapters", Chapter.class)
            .setParameter("chapters", chapters)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
