package com.jher.nid_aux_histoires.domain;

import static com.jher.nid_aux_histoires.domain.BookTestSamples.*;
import static com.jher.nid_aux_histoires.domain.ChapterTestSamples.*;
import static com.jher.nid_aux_histoires.domain.ImageTestSamples.*;
import static com.jher.nid_aux_histoires.domain.PartTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jher.nid_aux_histoires.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PartTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Part.class);
        Part part1 = getPartSample1();
        Part part2 = new Part();
        assertThat(part1).isNotEqualTo(part2);

        part2.setId(part1.getId());
        assertThat(part1).isEqualTo(part2);

        part2 = getPartSample2();
        assertThat(part1).isNotEqualTo(part2);
    }

    @Test
    void chapterTest() throws Exception {
        Part part = getPartRandomSampleGenerator();
        Chapter chapterBack = getChapterRandomSampleGenerator();

        part.addChapter(chapterBack);
        assertThat(part.getChapters()).containsOnly(chapterBack);
        assertThat(chapterBack.getPart()).isEqualTo(part);

        part.removeChapter(chapterBack);
        assertThat(part.getChapters()).doesNotContain(chapterBack);
        assertThat(chapterBack.getPart()).isNull();

        part.chapters(new HashSet<>(Set.of(chapterBack)));
        assertThat(part.getChapters()).containsOnly(chapterBack);
        assertThat(chapterBack.getPart()).isEqualTo(part);

        part.setChapters(new HashSet<>());
        assertThat(part.getChapters()).doesNotContain(chapterBack);
        assertThat(chapterBack.getPart()).isNull();
    }

    @Test
    void imageTest() throws Exception {
        Part part = getPartRandomSampleGenerator();
        Image imageBack = getImageRandomSampleGenerator();

        part.addImage(imageBack);
        assertThat(part.getImages()).containsOnly(imageBack);

        part.removeImage(imageBack);
        assertThat(part.getImages()).doesNotContain(imageBack);

        part.images(new HashSet<>(Set.of(imageBack)));
        assertThat(part.getImages()).containsOnly(imageBack);

        part.setImages(new HashSet<>());
        assertThat(part.getImages()).doesNotContain(imageBack);
    }

    @Test
    void bookTest() throws Exception {
        Part part = getPartRandomSampleGenerator();
        Book bookBack = getBookRandomSampleGenerator();

        part.setBook(bookBack);
        assertThat(part.getBook()).isEqualTo(bookBack);

        part.book(null);
        assertThat(part.getBook()).isNull();
    }
}
