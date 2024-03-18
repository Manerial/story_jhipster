package com.jher.nid_aux_histoires.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jher.nid_aux_histoires.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SceneDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SceneDTO.class);
        SceneDTO sceneDTO1 = new SceneDTO();
        sceneDTO1.setId(1L);
        SceneDTO sceneDTO2 = new SceneDTO();
        assertThat(sceneDTO1).isNotEqualTo(sceneDTO2);
        sceneDTO2.setId(sceneDTO1.getId());
        assertThat(sceneDTO1).isEqualTo(sceneDTO2);
        sceneDTO2.setId(2L);
        assertThat(sceneDTO1).isNotEqualTo(sceneDTO2);
        sceneDTO1.setId(null);
        assertThat(sceneDTO1).isNotEqualTo(sceneDTO2);
    }
}
