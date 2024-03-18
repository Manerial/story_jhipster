package com.jher.nid_aux_histoires.repository;

import com.jher.nid_aux_histoires.domain.Part;
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
public class PartRepositoryWithBagRelationshipsImpl implements PartRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Part> fetchBagRelationships(Optional<Part> part) {
        return part.map(this::fetchImages);
    }

    @Override
    public Page<Part> fetchBagRelationships(Page<Part> parts) {
        return new PageImpl<>(fetchBagRelationships(parts.getContent()), parts.getPageable(), parts.getTotalElements());
    }

    @Override
    public List<Part> fetchBagRelationships(List<Part> parts) {
        return Optional.of(parts).map(this::fetchImages).orElse(Collections.emptyList());
    }

    Part fetchImages(Part result) {
        return entityManager
            .createQuery("select part from Part part left join fetch part.images where part.id = :id", Part.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Part> fetchImages(List<Part> parts) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, parts.size()).forEach(index -> order.put(parts.get(index).getId(), index));
        List<Part> result = entityManager
            .createQuery("select part from Part part left join fetch part.images where part in :parts", Part.class)
            .setParameter("parts", parts)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
