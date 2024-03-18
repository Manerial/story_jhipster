package com.jher.nid_aux_histoires.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jher.nid_aux_histoires.web.rest.TestUtil;

public class SceneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scene.class);
        Scene scene1 = new Scene();
        scene1.setId(1L);
        Scene scene2 = new Scene();
        scene2.setId(scene1.getId());
        assertThat(scene1).isEqualTo(scene2);
        scene2.setId(2L);
        assertThat(scene1).isNotEqualTo(scene2);
        scene1.setId(null);
        assertThat(scene1).isNotEqualTo(scene2);
    }
}
