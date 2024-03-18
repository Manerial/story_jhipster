package com.jher.nid_aux_histoires.domain;

import static com.jher.nid_aux_histoires.domain.ChapterTestSamples.*;
import static com.jher.nid_aux_histoires.domain.ImageTestSamples.*;
import static com.jher.nid_aux_histoires.domain.SceneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jher.nid_aux_histoires.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SceneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scene.class);
        Scene scene1 = getSceneSample1();
        Scene scene2 = new Scene();
        assertThat(scene1).isNotEqualTo(scene2);

        scene2.setId(scene1.getId());
        assertThat(scene1).isEqualTo(scene2);

        scene2 = getSceneSample2();
        assertThat(scene1).isNotEqualTo(scene2);
    }

    @Test
    void imageTest() throws Exception {
        Scene scene = getSceneRandomSampleGenerator();
        Image imageBack = getImageRandomSampleGenerator();

        scene.addImage(imageBack);
        assertThat(scene.getImages()).containsOnly(imageBack);

        scene.removeImage(imageBack);
        assertThat(scene.getImages()).doesNotContain(imageBack);

        scene.images(new HashSet<>(Set.of(imageBack)));
        assertThat(scene.getImages()).containsOnly(imageBack);

        scene.setImages(new HashSet<>());
        assertThat(scene.getImages()).doesNotContain(imageBack);
    }

    @Test
    void chapterTest() throws Exception {
        Scene scene = getSceneRandomSampleGenerator();
        Chapter chapterBack = getChapterRandomSampleGenerator();

        scene.setChapter(chapterBack);
        assertThat(scene.getChapter()).isEqualTo(chapterBack);

        scene.chapter(null);
        assertThat(scene.getChapter()).isNull();
    }
}
