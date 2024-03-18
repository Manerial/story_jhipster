package com.jher.nid_aux_histoires.repository;

import com.jher.nid_aux_histoires.domain.Book;
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
public class BookRepositoryWithBagRelationshipsImpl implements BookRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Book> fetchBagRelationships(Optional<Book> book) {
        return book.map(this::fetchImages);
    }

    @Override
    public Page<Book> fetchBagRelationships(Page<Book> books) {
        return new PageImpl<>(fetchBagRelationships(books.getContent()), books.getPageable(), books.getTotalElements());
    }

    @Override
    public List<Book> fetchBagRelationships(List<Book> books) {
        return Optional.of(books).map(this::fetchImages).orElse(Collections.emptyList());
    }

    Book fetchImages(Book result) {
        return entityManager
            .createQuery("select book from Book book left join fetch book.images where book.id = :id", Book.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Book> fetchImages(List<Book> books) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, books.size()).forEach(index -> order.put(books.get(index).getId(), index));
        List<Book> result = entityManager
            .createQuery("select book from Book book left join fetch book.images where book in :books", Book.class)
            .setParameter("books", books)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
