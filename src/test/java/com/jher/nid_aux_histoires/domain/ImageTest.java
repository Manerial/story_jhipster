package com.jher.nid_aux_histoires.domain;

import static com.jher.nid_aux_histoires.domain.BookTestSamples.*;
import static com.jher.nid_aux_histoires.domain.ChapterTestSamples.*;
import static com.jher.nid_aux_histoires.domain.ImageTestSamples.*;
import static com.jher.nid_aux_histoires.domain.PartTestSamples.*;
import static com.jher.nid_aux_histoires.domain.SceneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jher.nid_aux_histoires.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ImageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Image.class);
        Image image1 = getImageSample1();
        Image image2 = new Image();
        assertThat(image1).isNotEqualTo(image2);

        image2.setId(image1.getId());
        assertThat(image1).isEqualTo(image2);

        image2 = getImageSample2();
        assertThat(image1).isNotEqualTo(image2);
    }

    @Test
    void bookToCoverTest() throws Exception {
        Image image = getImageRandomSampleGenerator();
        Book bookBack = getBookRandomSampleGenerator();

        image.addBookToCover(bookBack);
        assertThat(image.getBookToCovers()).containsOnly(bookBack);
        assertThat(bookBack.getCover()).isEqualTo(image);

        image.removeBookToCover(bookBack);
        assertThat(image.getBookToCovers()).doesNotContain(bookBack);
        assertThat(bookBack.getCover()).isNull();

        image.bookToCovers(new HashSet<>(Set.of(bookBack)));
        assertThat(image.getBookToCovers()).containsOnly(bookBack);
        assertThat(bookBack.getCover()).isEqualTo(image);

        image.setBookToCovers(new HashSet<>());
        assertThat(image.getBookToCovers()).doesNotContain(bookBack);
        assertThat(bookBack.getCover()).isNull();
    }

    @Test
    void bookTest() throws Exception {
        Image image = getImageRandomSampleGenerator();
        Book bookBack = getBookRandomSampleGenerator();

        image.addBook(bookBack);
        assertThat(image.getBooks()).containsOnly(bookBack);
        assertThat(bookBack.getImages()).containsOnly(image);

        image.removeBook(bookBack);
        assertThat(image.getBooks()).doesNotContain(bookBack);
        assertThat(bookBack.getImages()).doesNotContain(image);

        image.books(new HashSet<>(Set.of(bookBack)));
        assertThat(image.getBooks()).containsOnly(bookBack);
        assertThat(bookBack.getImages()).containsOnly(image);

        image.setBooks(new HashSet<>());
        assertThat(image.getBooks()).doesNotContain(bookBack);
        assertThat(bookBack.getImages()).doesNotContain(image);
    }

    @Test
    void partTest() throws Exception {
        Image image = getImageRandomSampleGenerator();
        Part partBack = getPartRandomSampleGenerator();

        image.addPart(partBack);
        assertThat(image.getParts()).containsOnly(partBack);
        assertThat(partBack.getImages()).containsOnly(image);

        image.removePart(partBack);
        assertThat(image.getParts()).doesNotContain(partBack);
        assertThat(partBack.getImages()).doesNotContain(image);

        image.parts(new HashSet<>(Set.of(partBack)));
        assertThat(image.getParts()).containsOnly(partBack);
        assertThat(partBack.getImages()).containsOnly(image);

        image.setParts(new HashSet<>());
        assertThat(image.getParts()).doesNotContain(partBack);
        assertThat(partBack.getImages()).doesNotContain(image);
    }

    @Test
    void chapterTest() throws Exception {
        Image image = getImageRandomSampleGenerator();
        Chapter chapterBack = getChapterRandomSampleGenerator();

        image.addChapter(chapterBack);
        assertThat(image.getChapters()).containsOnly(chapterBack);
        assertThat(chapterBack.getImages()).containsOnly(image);

        image.removeChapter(chapterBack);
        assertThat(image.getChapters()).doesNotContain(chapterBack);
        assertThat(chapterBack.getImages()).doesNotContain(image);

        image.chapters(new HashSet<>(Set.of(chapterBack)));
        assertThat(image.getChapters()).containsOnly(chapterBack);
        assertThat(chapterBack.getImages()).containsOnly(image);

        image.setChapters(new HashSet<>());
        assertThat(image.getChapters()).doesNotContain(chapterBack);
        assertThat(chapterBack.getImages()).doesNotContain(image);
    }

    @Test
    void sceneTest() throws Exception {
        Image image = getImageRandomSampleGenerator();
        Scene sceneBack = getSceneRandomSampleGenerator();

        image.addScene(sceneBack);
        assertThat(image.getScenes()).containsOnly(sceneBack);
        assertThat(sceneBack.getImages()).containsOnly(image);

        image.removeScene(sceneBack);
        assertThat(image.getScenes()).doesNotContain(sceneBack);
        assertThat(sceneBack.getImages()).doesNotContain(image);

        image.scenes(new HashSet<>(Set.of(sceneBack)));
        assertThat(image.getScenes()).containsOnly(sceneBack);
        assertThat(sceneBack.getImages()).containsOnly(image);

        image.setScenes(new HashSet<>());
        assertThat(image.getScenes()).doesNotContain(sceneBack);
        assertThat(sceneBack.getImages()).doesNotContain(image);
    }
}
