package com.jher.nid_aux_histoires.domain;

import static com.jher.nid_aux_histoires.domain.BookStatusTestSamples.*;
import static com.jher.nid_aux_histoires.domain.BookTestSamples.*;
import static com.jher.nid_aux_histoires.domain.ImageTestSamples.*;
import static com.jher.nid_aux_histoires.domain.PartTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jher.nid_aux_histoires.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Book.class);
        Book book1 = getBookSample1();
        Book book2 = new Book();
        assertThat(book1).isNotEqualTo(book2);

        book2.setId(book1.getId());
        assertThat(book1).isEqualTo(book2);

        book2 = getBookSample2();
        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    void partTest() throws Exception {
        Book book = getBookRandomSampleGenerator();
        Part partBack = getPartRandomSampleGenerator();

        book.addPart(partBack);
        assertThat(book.getParts()).containsOnly(partBack);
        assertThat(partBack.getBook()).isEqualTo(book);

        book.removePart(partBack);
        assertThat(book.getParts()).doesNotContain(partBack);
        assertThat(partBack.getBook()).isNull();

        book.parts(new HashSet<>(Set.of(partBack)));
        assertThat(book.getParts()).containsOnly(partBack);
        assertThat(partBack.getBook()).isEqualTo(book);

        book.setParts(new HashSet<>());
        assertThat(book.getParts()).doesNotContain(partBack);
        assertThat(partBack.getBook()).isNull();
    }

    @Test
    void imageTest() throws Exception {
        Book book = getBookRandomSampleGenerator();
        Image imageBack = getImageRandomSampleGenerator();

        book.addImage(imageBack);
        assertThat(book.getImages()).containsOnly(imageBack);

        book.removeImage(imageBack);
        assertThat(book.getImages()).doesNotContain(imageBack);

        book.images(new HashSet<>(Set.of(imageBack)));
        assertThat(book.getImages()).containsOnly(imageBack);

        book.setImages(new HashSet<>());
        assertThat(book.getImages()).doesNotContain(imageBack);
    }

    @Test
    void coverTest() throws Exception {
        Book book = getBookRandomSampleGenerator();
        Image imageBack = getImageRandomSampleGenerator();

        book.setCover(imageBack);
        assertThat(book.getCover()).isEqualTo(imageBack);

        book.cover(null);
        assertThat(book.getCover()).isNull();
    }

    @Test
    void bookStatusTest() throws Exception {
        Book book = getBookRandomSampleGenerator();
        BookStatus bookStatusBack = getBookStatusRandomSampleGenerator();

        book.addBookStatus(bookStatusBack);
        assertThat(book.getBookStatuses()).containsOnly(bookStatusBack);
        assertThat(bookStatusBack.getBook()).isEqualTo(book);

        book.removeBookStatus(bookStatusBack);
        assertThat(book.getBookStatuses()).doesNotContain(bookStatusBack);
        assertThat(bookStatusBack.getBook()).isNull();

        book.bookStatuses(new HashSet<>(Set.of(bookStatusBack)));
        assertThat(book.getBookStatuses()).containsOnly(bookStatusBack);
        assertThat(bookStatusBack.getBook()).isEqualTo(book);

        book.setBookStatuses(new HashSet<>());
        assertThat(book.getBookStatuses()).doesNotContain(bookStatusBack);
        assertThat(bookStatusBack.getBook()).isNull();
    }
}
