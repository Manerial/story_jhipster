package com.jher.nid_aux_histoires.domain;

import static com.jher.nid_aux_histoires.domain.IdeaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jher.nid_aux_histoires.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IdeaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Idea.class);
        Idea idea1 = getIdeaSample1();
        Idea idea2 = new Idea();
        assertThat(idea1).isNotEqualTo(idea2);

        idea2.setId(idea1.getId());
        assertThat(idea1).isEqualTo(idea2);

        idea2 = getIdeaSample2();
        assertThat(idea1).isNotEqualTo(idea2);
    }
}
