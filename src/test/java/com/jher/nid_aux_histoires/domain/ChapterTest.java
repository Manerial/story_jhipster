package com.jher.nid_aux_histoires.domain;

import static com.jher.nid_aux_histoires.domain.BookStatusTestSamples.*;
import static com.jher.nid_aux_histoires.domain.ChapterTestSamples.*;
import static com.jher.nid_aux_histoires.domain.ImageTestSamples.*;
import static com.jher.nid_aux_histoires.domain.PartTestSamples.*;
import static com.jher.nid_aux_histoires.domain.SceneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jher.nid_aux_histoires.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ChapterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chapter.class);
        Chapter chapter1 = getChapterSample1();
        Chapter chapter2 = new Chapter();
        assertThat(chapter1).isNotEqualTo(chapter2);

        chapter2.setId(chapter1.getId());
        assertThat(chapter1).isEqualTo(chapter2);

        chapter2 = getChapterSample2();
        assertThat(chapter1).isNotEqualTo(chapter2);
    }

    @Test
    void sceneTest() throws Exception {
        Chapter chapter = getChapterRandomSampleGenerator();
        Scene sceneBack = getSceneRandomSampleGenerator();

        chapter.addScene(sceneBack);
        assertThat(chapter.getScenes()).containsOnly(sceneBack);
        assertThat(sceneBack.getChapter()).isEqualTo(chapter);

        chapter.removeScene(sceneBack);
        assertThat(chapter.getScenes()).doesNotContain(sceneBack);
        assertThat(sceneBack.getChapter()).isNull();

        chapter.scenes(new HashSet<>(Set.of(sceneBack)));
        assertThat(chapter.getScenes()).containsOnly(sceneBack);
        assertThat(sceneBack.getChapter()).isEqualTo(chapter);

        chapter.setScenes(new HashSet<>());
        assertThat(chapter.getScenes()).doesNotContain(sceneBack);
        assertThat(sceneBack.getChapter()).isNull();
    }

    @Test
    void imageTest() throws Exception {
        Chapter chapter = getChapterRandomSampleGenerator();
        Image imageBack = getImageRandomSampleGenerator();

        chapter.addImage(imageBack);
        assertThat(chapter.getImages()).containsOnly(imageBack);

        chapter.removeImage(imageBack);
        assertThat(chapter.getImages()).doesNotContain(imageBack);

        chapter.images(new HashSet<>(Set.of(imageBack)));
        assertThat(chapter.getImages()).containsOnly(imageBack);

        chapter.setImages(new HashSet<>());
        assertThat(chapter.getImages()).doesNotContain(imageBack);
    }

    @Test
    void partTest() throws Exception {
        Chapter chapter = getChapterRandomSampleGenerator();
        Part partBack = getPartRandomSampleGenerator();

        chapter.setPart(partBack);
        assertThat(chapter.getPart()).isEqualTo(partBack);

        chapter.part(null);
        assertThat(chapter.getPart()).isNull();
    }

    @Test
    void bookStatusTest() throws Exception {
        Chapter chapter = getChapterRandomSampleGenerator();
        BookStatus bookStatusBack = getBookStatusRandomSampleGenerator();

        chapter.addBookStatus(bookStatusBack);
        assertThat(chapter.getBookStatuses()).containsOnly(bookStatusBack);
        assertThat(bookStatusBack.getCurentChapter()).isEqualTo(chapter);

        chapter.removeBookStatus(bookStatusBack);
        assertThat(chapter.getBookStatuses()).doesNotContain(bookStatusBack);
        assertThat(bookStatusBack.getCurentChapter()).isNull();

        chapter.bookStatuses(new HashSet<>(Set.of(bookStatusBack)));
        assertThat(chapter.getBookStatuses()).containsOnly(bookStatusBack);
        assertThat(bookStatusBack.getCurentChapter()).isEqualTo(chapter);

        chapter.setBookStatuses(new HashSet<>());
        assertThat(chapter.getBookStatuses()).doesNotContain(bookStatusBack);
        assertThat(bookStatusBack.getCurentChapter()).isNull();
    }
}
