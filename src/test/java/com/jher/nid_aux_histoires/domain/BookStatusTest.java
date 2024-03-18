package com.jher.nid_aux_histoires.domain;

import static com.jher.nid_aux_histoires.domain.BookStatusTestSamples.*;
import static com.jher.nid_aux_histoires.domain.BookTestSamples.*;
import static com.jher.nid_aux_histoires.domain.ChapterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jher.nid_aux_histoires.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookStatus.class);
        BookStatus bookStatus1 = getBookStatusSample1();
        BookStatus bookStatus2 = new BookStatus();
        assertThat(bookStatus1).isNotEqualTo(bookStatus2);

        bookStatus2.setId(bookStatus1.getId());
        assertThat(bookStatus1).isEqualTo(bookStatus2);

        bookStatus2 = getBookStatusSample2();
        assertThat(bookStatus1).isNotEqualTo(bookStatus2);
    }

    @Test
    void bookTest() throws Exception {
        BookStatus bookStatus = getBookStatusRandomSampleGenerator();
        Book bookBack = getBookRandomSampleGenerator();

        bookStatus.setBook(bookBack);
        assertThat(bookStatus.getBook()).isEqualTo(bookBack);

        bookStatus.book(null);
        assertThat(bookStatus.getBook()).isNull();
    }

    @Test
    void curentChapterTest() throws Exception {
        BookStatus bookStatus = getBookStatusRandomSampleGenerator();
        Chapter chapterBack = getChapterRandomSampleGenerator();

        bookStatus.setCurentChapter(chapterBack);
        assertThat(bookStatus.getCurentChapter()).isEqualTo(chapterBack);

        bookStatus.curentChapter(null);
        assertThat(bookStatus.getCurentChapter()).isNull();
    }
}
